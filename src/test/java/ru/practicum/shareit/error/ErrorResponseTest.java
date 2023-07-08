package ru.practicum.shareit.error;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.*;

@JsonTest
class ErrorResponseTest {

    @Autowired
    private JacksonTester<ErrorResponse> json;

    @Test
    void testSerialize() throws Exception {

        ErrorResponse errorResponse = new ErrorResponse("error", "description");
        var result = json.write(errorResponse);

        assertThat(result).hasJsonPath("$.error");
        assertThat(result).hasJsonPath("$.description");

        assertThat(result)
                .extractingJsonPathStringValue("$.error")
                .isEqualTo(errorResponse.getError());
        assertThat(result)
                .extractingJsonPathStringValue("$.description")
                .isEqualTo(errorResponse.getDescription());
    }
}