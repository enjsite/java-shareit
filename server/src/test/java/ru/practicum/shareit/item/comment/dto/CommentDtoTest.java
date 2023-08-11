package ru.practicum.shareit.item.comment.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class CommentDtoTest {

    @Autowired
    private JacksonTester<CommentDto> json;

    @Test
    void testSerialize() throws Exception {

        CommentDto commentDto = new CommentDto(1L, "text", "authorName", "2023-07-30");

        var result = json.write(commentDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.text");
        assertThat(result).hasJsonPath("$.authorName");
        assertThat(result).hasJsonPath("$.created");

        assertThat(result)
                .extractingJsonPathNumberValue("$.id")
                .isEqualTo(new Long(commentDto.getId()).intValue());
        assertThat(result)
                .extractingJsonPathStringValue("$.text")
                .isEqualTo(commentDto.getText());
        assertThat(result)
                .extractingJsonPathStringValue("$.authorName")
                .isEqualTo(commentDto.getAuthorName());
        assertThat(result)
                .extractingJsonPathStringValue("$.created")
                .isEqualTo(commentDto.getCreated());
    }
}