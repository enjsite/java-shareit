package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotSupportedException;
import ru.practicum.shareit.exception.ValidationException;

import java.util.List;

public interface BookingService {

    Booking add(long userId, BookingDto bookingDto) throws NotAvailableException, ValidationException;

    Booking approved(long userId, long bookingId, boolean approved) throws ValidationException;

    Booking get(long userId, long bookingId) throws NotAvailableException;

    List<Booking> getAll(long userId, String status) throws NotAvailableException, NotSupportedException;

    List<Booking> getAllForOwner(long userId, String status) throws NotAvailableException, NotSupportedException;
}
