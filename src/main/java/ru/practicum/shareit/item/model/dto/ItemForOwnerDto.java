package ru.practicum.shareit.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoForItems;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemForOwnerDto {

    private long id;

    private String name;

    private String description;

    private Boolean available;

    private User owner;

    private Long requestId;

    private BookingDtoForItems lastBooking;

    private BookingDtoForItems nextBooking;

    private List<CommentDto> comments = new ArrayList<>();

    public ItemForOwnerDto(long id, String name, String description, Boolean available, User owner, Long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.requestId = requestId;
    }
}
