package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private Item item;

    private User booker;

    @BeforeEach
    void setUp() {
        this.item = new Item();
        item.setId(3L);
        item.setName("Item");

        this.booker = new User();
        booker.setId(2L);
        booker.setName("Booker");
    }

    @SneakyThrows
    @Test
    void add() {

        long userId = 0L;
        var bookingDto = new BookingDto(1L, 3L, LocalDateTime.now(), LocalDateTime.now(), booker, item, BookingStatus.WAITING);

        when(bookingService.add(userId, bookingDto)).thenReturn(bookingDto);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/bookings")
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(bookingDto), result);
        verify(bookingService, times(1)).add(userId, bookingDto);
    }

    @SneakyThrows
    @Test
    void approved() {

        long userId = 0L;
        long bookingId = 1L;

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/bookings/{bookingId}?approved=true", bookingId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(bookingService, times(1)).approved(userId, bookingId, true);
    }

    @SneakyThrows
    @Test
    void get() {

        long userId = 0L;
        long bookingId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bookings/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).get(userId, bookingId);
    }

    @SneakyThrows
    @Test
    void getAll() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bookings/")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).getAll(userId, "ALL", 0, 100);
    }

    @SneakyThrows
    @Test
    void getAllForOwner() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bookings/owner")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).getAllForOwner(userId, "ALL", 0, 100);
    }
}