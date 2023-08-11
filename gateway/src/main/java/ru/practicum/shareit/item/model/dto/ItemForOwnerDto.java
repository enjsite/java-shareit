package ru.practicum.shareit.item.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDtoForItems;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemForOwnerDto {

    long id;

    String name;

    String description;

    Boolean available;

    UserDto owner;

    Long requestId;

    BookingDtoForItems lastBooking;

    BookingDtoForItems nextBooking;

    List<CommentDto> comments = new ArrayList<>();

    public ItemForOwnerDto(long id, String name, String description, Boolean available, UserDto owner, Long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.requestId = requestId;
    }
}
