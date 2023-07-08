package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

class ItemMappingTest {

    private User user;

    private ItemRequest itemRequest;

    @BeforeEach
    void setUp() {

        this.user = new User();
        user.setId(2L);
        user.setName("User");

        this.itemRequest = new ItemRequest();
        itemRequest.setId(3L);
    }

    @Test
    void toItemDto() {

        var original = new Item(1L, "itemName", "itemDescription", true, user, null);
        var result = ItemMapping.toItemDto(original);
        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getName(), result.getName());
        assertEquals(original.getDescription(), result.getDescription());
        assertEquals(original.getAvailable(), result.getAvailable());
        assertEquals(original.getOwner(), result.getOwner());
    }

    @Test
    void toItemForOwnerDto() {

        var original = new Item(1L, "itemName", "itemDescription", true, user, null);
        var result = ItemMapping.toItemForOwnerDto(original);
        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getName(), result.getName());
        assertEquals(original.getDescription(), result.getDescription());
        assertEquals(original.getAvailable(), result.getAvailable());
        assertEquals(original.getOwner(), result.getOwner());
    }

    @Test
    void toItemForRequestDto() {

        var original = new Item(1L, "itemName", "itemDescription", true, user, itemRequest);
        var result = ItemMapping.toItemForRequestDto(original);
        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getName(), result.getName());
        assertEquals(original.getDescription(), result.getDescription());
        assertEquals(original.getAvailable(), result.getAvailable());
        assertEquals(original.getOwner().getId(), result.getOwnerId());
        assertEquals(original.getRequest().getId(), result.getRequestId());
    }

    @Test
    void mapToItem() {

        var original = new ItemDto(1L, "itemName", "itemDescriiption", true, user, itemRequest.getId());
        var result = ItemMapping.mapToItem(original, user, itemRequest);

        assertNotNull(result);
        assertNotNull(result.getOwner());
        assertNotNull(result.getRequest());
    }
}