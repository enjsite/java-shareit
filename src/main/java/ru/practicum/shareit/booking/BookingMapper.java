package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BookingMapper {

    static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static BookingDto toBookingDto(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getItem().getId(),
                booking.getStart().toString(),
                booking.getEnd().toString(),
                booking.getBooker().getId()
        );
    }

    public static Booking mapToBooking(BookingDto bookingDto, User booker, Item item) throws ValidationException {

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setBooker(booker);

        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new ValidationException("Дата не задана");
        }
        booking.setStart(LocalDateTime.from(formatter.parse(bookingDto.getStart())));
        booking.setEnd(LocalDateTime.from(formatter.parse(bookingDto.getEnd())));

        return booking;
    }
}
