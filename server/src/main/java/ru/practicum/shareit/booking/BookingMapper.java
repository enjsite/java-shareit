package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForItems;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Service
public class BookingMapper {

    public static BookingDto toBookingDto(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getItem().getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker(),
                booking.getItem(),
                booking.getStatus()
        );
    }

    public static BookingDtoForItems toBookingDtoForItems(Booking booking) {

        return new BookingDtoForItems(
                booking.getId(),
                booking.getItem().getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

    public static Booking mapToBooking(BookingDto bookingDto, User booker, Item item) throws ValidationException {

        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setItem(item);
        booking.setBooker(booker);

        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new ValidationException("Дата не задана");
        }
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        return booking;
    }
}
