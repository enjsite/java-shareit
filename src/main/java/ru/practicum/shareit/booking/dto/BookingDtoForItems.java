package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoForItems {

    private long id;

    private long itemId;

    private LocalDateTime start;

    private LocalDateTime end;

    private long bookerId;

    private BookingStatus status = BookingStatus.WAITING;

    public BookingDtoForItems(long id, long itemId, LocalDateTime start, LocalDateTime end, long bookerId) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
    }
}
