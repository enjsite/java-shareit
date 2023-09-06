package ru.practicum.shareit.booking.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {

    long id;

    long itemId;

    LocalDateTime start;

    LocalDateTime end;

    UserDto booker;

    BookingStatus status = BookingStatus.WAITING;

    ItemDto item;

    public BookingDto(long id, long itemId, LocalDateTime start, LocalDateTime end, UserDto booker, ItemDto item,
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
