package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    private Item item;

    private User booker;

    @BeforeEach
    void setUp() {
        this.item = new Item();
        item.setId(3L);
        item.setName("Item");

        this.booker = new User();
        booker.setId(2L);
        booker.setName("Booker");
    }

    @Test
    void toBookingDto() {

        var original = new Booking();
        original.setId(1L);
        original.setItem(item);
        original.setStart(LocalDateTime.now());
        original.setEnd(LocalDateTime.now());
        original.setBooker(booker);
        original.setStatus(BookingStatus.APPROVED);

        var result = BookingMapper.toBookingDto(original);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(original.getId(), result.getId());
        Assertions.assertEquals(original.getItem().getId(), result.getItemId());
        Assertions.assertEquals(original.getItem().getId(), result.getItem().getId());
        Assertions.assertEquals(original.getItem().getName(), result.getItem().getName());
        Assertions.assertEquals(original.getStart(), result.getStart());
        Assertions.assertEquals(original.getEnd(), result.getEnd());
        Assertions.assertEquals(original.getBooker().getId(), result.getBooker().getId());
        Assertions.assertEquals(original.getBooker().getName(), result.getBooker().getName());
        Assertions.assertEquals(original.getStatus(), result.getStatus());
    }

    @Test
    void toBookingDtoForItems() {

        var original = new Booking();
        original.setId(1L);
        original.setItem(item);
        original.setStart(LocalDateTime.now());
        original.setEnd(LocalDateTime.now());
        original.setBooker(booker);
        original.setStatus(BookingStatus.APPROVED);

        var result = BookingMapper.toBookingDtoForItems(original);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(original.getId(), result.getId());
        Assertions.assertEquals(original.getItem().getId(), result.getItemId());
        Assertions.assertEquals(original.getStart(), result.getStart());
        Assertions.assertEquals(original.getEnd(), result.getEnd());
        Assertions.assertEquals(original.getBooker().getId(), result.getBookerId());
        Assertions.assertEquals(original.getStatus(), result.getStatus());
    }

    @Test
    void mapToBooking() throws ValidationException {

        var original = new BookingDto(1L, 3L, LocalDateTime.now(), LocalDateTime.now(), booker, item, BookingStatus.WAITING);
        var result = BookingMapper.mapToBooking(original, booker, item);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getItem());
        Assertions.assertNotNull(result.getBooker());
    }
}