package ru.practicum.shareit.item.model.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.user.model.User;

import static org.assertj.core.api.Assertions.*;

@JsonTest
class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void testSerialize() throws Exception {

        var user = new User();
        user.setId(2L);
        user.setName("User");
        ItemDto itemDto = new ItemDto(1L, "itemName", "itemDescriiption", true, user, null);

        var result = json.write(itemDto);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.description");
        assertThat(result).hasJsonPath("$.available");
        assertThat(result).hasJsonPath("$.owner");
        assertThat(result).hasJsonPath("$.requestId");

        assertThat(result)
                .extractingJsonPathNumberValue("$.id")
                .isEqualTo(new Long(itemDto.getId()).intValue());
        assertThat(result)
                .extractingJsonPathStringValue("$.name")
                .isEqualTo(itemDto.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.description")
                .isEqualTo(itemDto.getDescription());
        assertThat(result)
                .extractingJsonPathBooleanValue("$.available")
                .isEqualTo(itemDto.getAvailable());

    }
}