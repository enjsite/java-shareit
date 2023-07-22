package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @MockBean
    private ItemService itemService;

    private User owner;

    @BeforeEach
    void setUp() {

        this.owner = new User();
        owner.setId(2L);
        owner.setName("Owner");
    }

    @SneakyThrows
    @Test
    void get() {

        long userId = 0L;
        long itemId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        verify(itemService, times(1)).get(userId, itemId);
    }

    @SneakyThrows
    @Test
    void getAllItemsByUserId() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/items/")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItems(userId);
    }

    @SneakyThrows
    @Test
    void search() {

        long userId = 0L;

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/items/search?text=txt")
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());
        verify(itemService, times(1)).search(userId, "txt");
    }

    @SneakyThrows
    @Test
    void add() {

        long userId = owner.getId();
        var itemDto = new ItemDto(3L, "updatedItemName", "updatedItemDescription", true, owner, null);

        when(itemService.addNewItem(userId, itemDto)).thenReturn(itemDto);
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemDto), result);
        verify(itemService, times(1)).addNewItem(userId, itemDto);
    }

    @SneakyThrows
    @Test
    void update() {

        long userId = owner.getId();
        var itemDto = new ItemDto(3L, "updatedItemName", "updatedItemDescription", true, owner, null);
        long itemId = itemDto.getId();

        when(itemService.update(itemId, itemDto, userId)).thenReturn(itemDto);
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/items/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(itemDto), result);
        verify(itemService, times(1)).update(itemId, itemDto, userId);
    }

    @SneakyThrows
    @Test
    void deleteItem() {

        long userId = owner.getId();
        var itemForOwner = new ItemForOwnerDto();
        itemForOwner.setId(3L);
        itemForOwner.setOwner(owner);

        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(itemService.get(userId, itemForOwner.getId())).thenReturn(itemForOwner);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/items/{itemId}", itemForOwner.getId())
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemService, times(1)).deleteItem(userId, itemForOwner.getId());
    }

    @SneakyThrows
    @Test
    void addComment() {

        long userId = 1L;
        long itemId = 2L;
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        when(itemService.addComment(userId, itemId, comment)).thenReturn(commentDto);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/items/{itemId}/comment", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(itemService, times(1)).addComment(userId, itemId, comment);
    }
}