package ru.practicum.shareit.item.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private Item item;

    private User author;

    @BeforeEach
    void setUp() {

        this.item = new Item();
        item.setId(3L);
        item.setName("Item");

        this.author = new User();
        author.setId(2L);
        author.setName("Booker");
    }

    @Test
    void toCommentDto() {

        var original = new Comment(1L, "comment", item, author, LocalDateTime.now());
        var result = CommentMapper.toCommentDto(original);
        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals(original.getText(), result.getText());
        assertEquals(original.getAuthor().getName(), result.getAuthorName());
    }
}