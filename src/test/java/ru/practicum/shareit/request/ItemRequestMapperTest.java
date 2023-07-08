package ru.practicum.shareit.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestMapperTest {

    private User user;

    @BeforeEach
    void setUp() {

        this.user = new User();
        user.setId(2L);
        user.setName("User");
    }

    @Test
    void toItemRequestDto() {

        var original = new ItemRequest(1L, "description", user, LocalDateTime.now());
        var result = ItemRequestMapper.toItemRequestDto(original);
        assertNotNull(result);
        Assertions.assertEquals(original.getId(), result.getId());
        Assertions.assertEquals(original.getDescription(), result.getDescription());
        Assertions.assertEquals(original.getRequestor(), result.getRequestor());
    }

    @Test
    void mapToItemRequest() {

        var original = new ItemRequestDto(1L, "description", user, LocalDateTime.now());
        var result = ItemRequestMapper.mapToItemRequest(original);
        assertNotNull(result);
        Assertions.assertEquals(original.getId(), result.getId());
        Assertions.assertEquals(original.getDescription(), result.getDescription());
        Assertions.assertEquals(original.getRequestor(), result.getRequestor());
    }
}