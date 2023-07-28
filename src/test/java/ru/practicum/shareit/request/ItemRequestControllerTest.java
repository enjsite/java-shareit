package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemRequestController.class)
@AutoConfigureMockMvc
class ItemRequestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @MockBean
    private ItemRequestService itemRequestService;

    @SneakyThrows
    @Test
    void add() {

        long userId = 1L;
        var requestor = new User(userId, "NameR", "requestor@mail.ru");
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("descr");
        itemRequestDto.setRequestor(requestor);
        itemRequestDto.setCreated(LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(requestor));
        when(itemRequestService.add(userId, itemRequestDto)).thenReturn(itemRequestDto);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/requests")
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemRequestDto), result);
        verify(itemRequestService, times(1)).add(userId, itemRequestDto);
    }

    @SneakyThrows
    @Test
    void getByRequestor() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/requests")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemRequestService, times(1)).getByRequestorId(userId);
    }

    @SneakyThrows
    @Test
    void get() {

        long userId = 0L;
        long requestId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/requests/{requestId}", requestId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemRequestService, times(1)).get(userId, requestId);
    }

    @SneakyThrows
    @Test
    void getAll() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/requests/all")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemRequestService, times(1)).getAll(userId, 0, 100);
    }
}