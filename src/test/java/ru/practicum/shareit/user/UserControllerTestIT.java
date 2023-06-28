package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class UserControllerTestIT {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void get() {

        long userId = 0L;

        //mockMvc.perform(get("/users/{userId}", userId)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveNewUser() {
    }

    @Test
    void update() {
    }
}