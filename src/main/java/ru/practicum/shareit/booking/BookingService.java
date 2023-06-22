package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotSupportedException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

public interface BookingService {

    BookingDto add(long userId, BookingDto bookingDto) throws NotAvailableException, ValidationException;

    BookingDto approved(long userId, long bookingId, boolean approved) throws ValidationException;

    BookingDto get(long userId, long bookingId) throws NotAvailableException;

    List<BookingDto> getAll(long userId, String status) throws NotAvailableException, NotSupportedException;

    List<BookingDto> getAllForOwner(long userId, String status) throws NotAvailableException, NotSupportedException;
}
