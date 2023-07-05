package ru.practicum.shareit.booking.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@JsonTest
class BookingDtoJsonTest {

    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void testSerialize() throws Exception {

        var booker = new User(2L, "Booker", "booker@mail.ru");
        var item = new Item(3L, "Item", "Item description", true);
        BookingDto bookingDto = new BookingDto(1L, 3L, LocalDateTime.now(), LocalDateTime.now(), booker, item, BookingStatus.WAITING);

        var result = json.write(bookingDto);
        Assertions.assertThat(result).hasJsonPath("$.id");
        Assertions.assertThat(result).hasJsonPath("$.itemId");
        Assertions.assertThat(result).hasJsonPath("$.start");
        Assertions.assertThat(result).hasJsonPath("$.end");
        Assertions.assertThat(result).hasJsonPath("$.booker");
        Assertions.assertThat(result).hasJsonPath("$.item");
        Assertions.assertThat(result).hasJsonPath("$.status");

        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.id")
                .isEqualTo(new Long(bookingDto.getId()).intValue());
        Assertions.assertThat(result).hasJsonPathValue("$.start");
        Assertions.assertThat(result).hasJsonPathValue("$.end");
        Assertions.assertThat(result)
                .extractingJsonPathStringValue("$.status")
                .isEqualTo(bookingDto.getStatus().toString());
        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.itemId")
                .isEqualTo(new Long(bookingDto.getItemId()).intValue());

        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.booker.id")
                .isEqualTo(new Long(booker.getId()).intValue());
        Assertions.assertThat(result)
                .extractingJsonPathStringValue("$.booker.name")
                .isEqualTo(booker.getName());
        Assertions.assertThat(result)
                .extractingJsonPathStringValue("$.booker.email")
                .isEqualTo(booker.getEmail());

        Assertions.assertThat(result)
                .extractingJsonPathNumberValue("$.item.id")
                .isEqualTo(new Long(item.getId()).intValue());
        Assertions.assertThat(result)
                .extractingJsonPathStringValue("$.item.name")
                .isEqualTo(item.getName());
        Assertions.assertThat(result)
                .extractingJsonPathStringValue("$.item.description")
                .isEqualTo(item.getDescription());
        Assertions.assertThat(result)
                .extractingJsonPathBooleanValue("$.item.available")
                .isEqualTo(item.getAvailable());
    }
}