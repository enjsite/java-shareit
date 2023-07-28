package ru.practicum.shareit.booking.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.BookingStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class BookingDtoForItems {

    long id;

    long itemId;

    LocalDateTime start;

    LocalDateTime end;

    long bookerId;

    BookingStatus status = BookingStatus.WAITING;

    public BookingDtoForItems(long id, long itemId, LocalDateTime start, LocalDateTime end, long bookerId) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
    }
}
