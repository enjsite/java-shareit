package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private long id;

    private long itemId;

    private String start;

    private String end;

    private long bookerId;

    private BookingStatus status = BookingStatus.WAITING;

    public BookingDto(long itemId, String start, String end, long bookerId) {
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
    }

    public BookingDto(long id, long itemId, String start, String end, long bookerId) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
    }
}
