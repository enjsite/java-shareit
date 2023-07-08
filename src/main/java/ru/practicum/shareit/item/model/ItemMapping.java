package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;
import ru.practicum.shareit.item.model.dto.ItemForRequestDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class ItemMapping {

    public static ItemDto toItemDto(Item item) {

        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest() != null ? item.getRequest().getId() : null
        );
    }

    public static ItemForOwnerDto toItemForOwnerDto(Item item) {

        return new ItemForOwnerDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest() != null ? item.getRequest().getId() : null
        );
    }

    public static ItemForRequestDto toItemForRequestDto(Item item) {

        return new ItemForRequestDto(
                item.getId(),
                item.getName(),
                item.getOwner().getId(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest().getId()
        );
    }

    public static Item mapToItem(ItemDto itemDto, User user, ItemRequest itemRequest) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        item.setOwner(user);
        item.setRequest(itemRequest);

        return item;
    }
}
