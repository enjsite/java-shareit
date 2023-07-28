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
    public BookingDto add(@RequestHeader("X-Sharer-User-Id") long userId,
                    @Valid @RequestBody BookingDto bookingDto) throws NotAvailableException, ValidationException {
        log.info(String.format("Получен запрос на создание нового бронирования:  от пользователя %d", userId));

        return bookingService.add(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approved(@RequestHeader("X-Sharer-User-Id") long userId,
                            @PathVariable Integer bookingId,
                            @RequestParam boolean approved) throws ValidationException {

        log.info(String.format("Получен запрос на подтвержение бронирования %d", userId));

        return bookingService.approved(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Integer bookingId) throws NotAvailableException {
        log.info(String.format("Запрос на просмотр бронирования %d", bookingId));
        return bookingService.get(userId, bookingId);
    }

    @GetMapping({"/", ""})
    public List<BookingDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @RequestParam(defaultValue = "ALL", required = false) String state,
                                   @RequestParam(required = false, defaultValue = "0") Integer from,
                                   @RequestParam(required = false, defaultValue = "100") Integer size) throws NotAvailableException, NotSupportedException, ValidationException {
        log.info(String.format("Запрос на просмотр бронирований от пользователя %d", userId));
        return bookingService.getAll(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllForOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @RequestParam(defaultValue = "ALL", required = false) String state,
                                           @RequestParam(required = false, defaultValue = "0") Integer from,
                                           @RequestParam(required = false, defaultValue = "100") Integer size) throws NotAvailableException, NotSupportedException, ValidationException {
        log.info(String.format("Запрос на просмотр бронирований от пользователя %d", userId));
        return bookingService.getAllForOwner(userId, state, from, size);
    }


}
