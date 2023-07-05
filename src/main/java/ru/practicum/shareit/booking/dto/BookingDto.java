package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private long id;

    private long itemId;

    private LocalDateTime start;

    private LocalDateTime end;

    private User booker;

    private BookingStatus status = BookingStatus.WAITING;

    private Item item;

    public BookingDto(long id, long itemId, LocalDateTime start, LocalDateTime end, User booker, Item item,
                      BookingStatus status) {
        this.id = id;
        this.itemId = itemId;
        this.start = start;
        this.end = end;
        this.booker = booker;
        this.item = item;
        this.status = status;
    }
}
