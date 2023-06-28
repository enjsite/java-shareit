package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotSupportedException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingDto add(long userId, BookingDto bookingDto) throws NotAvailableException, ValidationException {
        User booker = userRepository.findById(userId).orElseThrow();
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow();
        if (!item.getAvailable()) {
            throw new NotAvailableException("Вещь недоступна");
        }
        if (item.getOwner().getId() == userId) {
            throw new NotFoundException("Нельзя забронировать свою вещь.");
        }

        var booking = BookingMapper.mapToBooking(bookingDto, booker, item);
        validate(booking);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto approved(long userId, long bookingId, boolean approved) throws ValidationException {
        User user = userRepository.findById(userId).orElseThrow();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException("Пользователь не является владельцем вещи.");
        }
        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new ValidationException("Вещь уже забронирована.");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        var updatedBooking = bookingRepository.save(booking);
        return BookingMapper.toBookingDto(updatedBooking);
    }

    @Override
    public BookingDto get(long userId, long bookingId) throws NotAvailableException {
        User user = userRepository.findById(userId).orElseThrow();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();

        if (!(user.getId() == booking.getBooker().getId() || user.getId() == booking.getItem().getOwner().getId())) {
            throw new NotFoundException("Нет доступа");
        }

        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAll(long userId, String status, Integer from, Integer size) throws NotSupportedException, ValidationException {
        User user = userRepository.findById(userId).orElseThrow();

        if (from == null && size == null) {
            from = 0;
            size = 100;
        } else if (from < 0 || size < 1) {
            throw new ValidationException("Недопустимые значения пагинации");
        }

        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "start"));

        List<Booking> bookings;

        switch (status) {
            case "ALL":
                bookings = bookingRepository.findAllByBookerOrderByStartDesc(user, pageable).getContent();
                break;
            case "CURRENT":
                bookings = bookingRepository.findAllByBookerCurrentOrderByStartDesc(user, LocalDateTime.now());
                break;
            case "FUTURE":
                bookings = bookingRepository.findAllByBookerFutureOrderByStartDesc(user, LocalDateTime.now());
                break;
            case "PAST":
                bookings = bookingRepository.findAllByBookerAndEndBeforeOrderByStartDesc(user, LocalDateTime.now());
                break;
            case "REJECTED":
                bookings = bookingRepository.findAllByBookerAndStatusOrderByStartDesc(user, BookingStatus.REJECTED);
                break;
            case "WAITING":
                bookings = bookingRepository.findAllByBookerAndStatusOrderByStartDesc(user, BookingStatus.WAITING);
                break;

            default:
                throw new NotSupportedException("Unknown state: UNSUPPORTED_STATUS");
        }

        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllForOwner(long userId, String status, Integer from, Integer size) throws NotSupportedException, ValidationException {
        User user = userRepository.findById(userId).orElseThrow();

        if (from == null && size == null) {
            from = 0;
            size = 100;
        } else if (from < 0 || size < 1) {
            throw new ValidationException("Недопустимые значения пагинации");
        }
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.DESC, "start"));

        List<Booking> bookings = new ArrayList<>();

        switch (status) {
            case "ALL":
                bookings = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId, pageable).getContent();
                break;
            case "CURRENT":
                bookings = bookingRepository.findAllByItemOwnerIdCurrentOrderByStartDesc(user, LocalDateTime.now());
                break;
            case "FUTURE":
                bookings = bookingRepository.findAllByItemOwnerIdFutureOrderByStartDesc(user, LocalDateTime.now());
                break;
            case "PAST":
                bookings = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(user.getId(), LocalDateTime.now());
                break;
            case "REJECTED":
                bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(user.getId(), BookingStatus.REJECTED);
                break;
            case "WAITING":
                bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(user.getId(), BookingStatus.WAITING);
                break;

            default:
                throw new NotSupportedException("Unknown state: UNSUPPORTED_STATUS");
        }

        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }

    private void validate(Booking booking) throws ValidationException {
        if (booking.getStart() == null || booking.getEnd() == null) {
            throw new ValidationException("Дата не задана.");
        }
        if (booking.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата окончания в прошлом.");
        }
        if (booking.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата старта в прошлом.");
        }
        if (booking.getEnd().isBefore(booking.getStart())) {
            throw new ValidationException("Дата окончания раньше даты старта.");
        }
        if (booking.getEnd().isEqual(booking.getStart())) {
            throw new ValidationException("Дата окончания равна дате старта.");
        }
    }
}
