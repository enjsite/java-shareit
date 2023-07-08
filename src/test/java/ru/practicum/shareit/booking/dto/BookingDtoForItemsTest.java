package ru.practicum.shareit.booking.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;

@JsonTest
class BookingDtoForItemsTest {

    @Autowired
    private JacksonTester<BookingDtoForItems> json;

    @Test
    void testSerialize() throws Exception {

        BookingDtoForItems bookingDtoForItems =
                new BookingDtoForItems(1L, 2L, LocalDateTime.now(), LocalDateTime.now(), 3L);

        var result = json.write(bookingDtoForItems);
        Assertions.assertThat(result).hasJsonPath("$.id");
        Assertions.assertThat(result).hasJsonPath("$.itemId");
        Assertions.assertThat(result).hasJsonPath("$.start");
        Assertions.assertThat(result).hasJsonPath("$.end");
        Assertions.assertThat(result).hasJsonPath("$.bookerId");
        Assertions.assertThat(result).hasJsonPath("$.status");

        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.id")
                .isEqualTo(new Long(bookingDtoForItems.getId()).intValue());
        Assertions.assertThat(result).hasJsonPathValue("$.start");
        Assertions.assertThat(result).hasJsonPathValue("$.end");
        Assertions.assertThat(result)
                .extractingJsonPathStringValue("$.status")
                .isEqualTo(bookingDtoForItems.getStatus().toString());
        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.itemId")
                .isEqualTo(new Long(bookingDtoForItems.getItemId()).intValue());
        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.bookerId")
                .isEqualTo(new Long(bookingDtoForItems.getBookerId()).intValue());

    }
}