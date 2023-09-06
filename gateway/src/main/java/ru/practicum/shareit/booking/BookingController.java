package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;
	private static final String userIdHeader = "X-Sharer-User-Id";

	@PostMapping
	public ResponseEntity<Object> add(@RequestHeader(userIdHeader) long userId,
									  @RequestBody @Valid BookingDto bookingDto) {
		log.info("Creating booking {}, userId={}", bookingDto, userId);
		return bookingClient.add(userId, bookingDto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> approved(@RequestHeader(userIdHeader) long userId,
											@PathVariable Integer bookingId,
											@RequestParam boolean approved) {
		log.info(String.format("Получен запрос на подтвержение бронирования %d", userId));

		return bookingClient.approved(userId, bookingId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBooking(@RequestHeader(userIdHeader) long userId,
											 @PathVariable Long bookingId) {
		log.info("Get booking {}, userId={}", bookingId, userId);
		return bookingClient.getBooking(userId, bookingId);
	}

	@GetMapping({"/", ""})
	public ResponseEntity<Object> getBookings(@RequestHeader(userIdHeader) long userId,
			@RequestParam(name = "state", defaultValue = "ALL") String state,
			@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
			@Positive @RequestParam(name = "size", defaultValue = "100") Integer size) {
		BookingState bookingState = BookingState.from(state)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + state));
		log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
		return bookingClient.getBookings(userId, bookingState, from, size);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getAllForOwner(@RequestHeader(userIdHeader) long userId,
											  @RequestParam(name = "state", defaultValue = "ALL") String state,
											  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
											  @Positive @RequestParam(name = "size", defaultValue = "100") Integer size) {
		BookingState bookingState = BookingState.from(state)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + state));
		log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
		return bookingClient.getAllForOwner(userId, bookingState, from, size);
	}


}
