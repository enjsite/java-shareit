package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotSupportedException;
import ru.practicum.shareit.exception.ValidationException;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking add(@RequestHeader("X-Sharer-User-Id") long userId,
                    @Valid @RequestBody BookingDto bookingDto) throws NotAvailableException, ValidationException {
        log.info("Получен запрос на создание нового бронирования:  от пользователя " + userId);

        return bookingService.add(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public Booking approved(@RequestHeader("X-Sharer-User-Id") long userId,
                            @PathVariable Integer bookingId,
                            @RequestParam boolean approved) throws ValidationException {

        log.info("Получен запрос на подтвержение бронирования " + userId);

        return bookingService.approved(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public Booking get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Integer bookingId) throws NotAvailableException {
        log.info("Запрос на просмотр бронирования " + bookingId);
        return bookingService.get(userId, bookingId);
    }

    @GetMapping
    public List<Booking> getAll_(@RequestHeader("X-Sharer-User-Id") long userId,
                                @RequestParam(defaultValue = "ALL", required = false) String state) throws NotAvailableException, NotSupportedException {
        log.info("Запрос на просмотр бронирований от пользователя " + userId);
        return bookingService.getAll(userId, state);
    }

    @GetMapping("/")
    public List<Booking> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                @RequestParam(defaultValue = "ALL", required = false) String state) throws NotAvailableException, NotSupportedException {
        log.info("Запрос на просмотр бронирований от пользователя " + userId);
        return bookingService.getAll(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getAllForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                @RequestParam(defaultValue = "ALL", required = false) String state) throws NotAvailableException, NotSupportedException {
        log.info("Запрос на просмотр бронирований от пользователя " + userId);
        return bookingService.getAllForOwner(userId, state);
    }


}
