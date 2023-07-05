package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookingStatusTest {

    @Test
    void valueOf() {

        Assertions.assertEquals(BookingStatus.ALL, BookingStatus.valueOf("ALL"));
        Assertions.assertEquals(BookingStatus.CURRENT, BookingStatus.valueOf("CURRENT"));
        Assertions.assertEquals(BookingStatus.FUTURE, BookingStatus.valueOf("FUTURE"));
        Assertions.assertEquals(BookingStatus.PAST, BookingStatus.valueOf("PAST"));
        Assertions.assertEquals(BookingStatus.WAITING, BookingStatus.valueOf("WAITING"));
        Assertions.assertEquals(BookingStatus.APPROVED, BookingStatus.valueOf("APPROVED"));
        Assertions.assertEquals(BookingStatus.REJECTED, BookingStatus.valueOf("REJECTED"));
        Assertions.assertEquals(BookingStatus.CANCELED, BookingStatus.valueOf("CANCELED"));
    }
}