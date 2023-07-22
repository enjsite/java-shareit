package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTestIT {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @SneakyThrows
    @Test
    void get() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/{userId}", userId))
                .andExpect(status().isOk());
        verify(userService, times(1)).get(userId);
    }

    @SneakyThrows
    @Test
    void saveNewUser() {

        UserDto userDto = new UserDto();
        userDto.setEmail("email@mail.ru");
        userDto.setName("Name");

        when(userService.create(userDto)).thenReturn(userDto);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(userDto), result);
        verify(userService, times(1)).create(userDto);
    }

    @SneakyThrows
    @Test
    void updateUser() {

        Long userId = 0L;

        UserDto userDto = new UserDto();
        userDto.setEmail("email@mail.ru");
        userDto.setName("NewName");

        when(userService.update(userId, userDto)).thenReturn(userDto);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/{userId}", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(userDto), result);
        verify(userService, times(1)).update(userId, userDto);
    }

    @SneakyThrows
    @Test
    void removeUserById() {

        Long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{userId}", userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).removeUserById(userId);
    }

}