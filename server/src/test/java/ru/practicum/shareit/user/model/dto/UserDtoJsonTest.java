package ru.practicum.shareit.user.model.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.*;

@JsonTest
class UserDtoJsonTest {

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void testSerialize() throws Exception {

        UserDto userDto = new UserDto(1L, "userName", "userEmail@mail.ru");
        var result = json.write(userDto);

        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.name");
        assertThat(result).hasJsonPath("$.email");

        assertThat(result)
                .extractingJsonPathNumberValue("$.id")
                .isEqualTo(new Long(userDto.getId()).intValue());
        assertThat(result)
                .extractingJsonPathStringValue("$.name")
                .isEqualTo(userDto.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.email")
                .isEqualTo(userDto.getEmail());
    }
}