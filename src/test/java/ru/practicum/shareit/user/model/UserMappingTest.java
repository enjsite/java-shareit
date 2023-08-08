package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

class UserMappingTest {

    @Test
    void toUserDto() {
        var original = new User(1L, "userName", "userEmail@mail.ru");
        var result = UserMapping.toUserDto(original);

        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getName(), result.getName());
        assertEquals(original.getEmail(), result.getEmail());
    }

    @Test
    void mapToUser() {

        var original = new UserDto(1L, "userName", "userEmail@mail.ru");
        var result = UserMapping.mapToUser(original);

        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getName(), result.getName());
        assertEquals(original.getEmail(), result.getEmail());
    }
}