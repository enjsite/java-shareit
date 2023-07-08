package ru.practicum.shareit.booking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@JsonTest
public class BookingTest {

    @Autowired
    private JacksonTester<Booking> json;

    @Test
    void testSerialize() throws Exception {

        var booker = new User(2L, "Booker", "booker@mail.ru");
        var item = new Item(3L, "Item", "Item description", true);
        Booking booking = new Booking(1L, item, LocalDateTime.now(), LocalDateTime.now(), booker, BookingStatus.WAITING);

        var result = json.write(booking);
        assertThat(result).hasJsonPath("$.id");
        assertThat(result).hasJsonPath("$.item");
        assertThat(result).hasJsonPath("$.start");
        assertThat(result).hasJsonPath("$.end");
        assertThat(result).hasJsonPath("$.booker");
        assertThat(result).hasJsonPath("$.status");

        assertThat(result)
                .extractingJsonPathNumberValue("$.id")
                .isEqualTo(new Long(booking.getId()).intValue());
        assertThat(result).hasJsonPathValue("$.start");
        assertThat(result).hasJsonPathValue("$.end");
        assertThat(result)
                .extractingJsonPathStringValue("$.status")
                .isEqualTo(booking.getStatus().toString());

        assertThat(result)
                .extractingJsonPathNumberValue("$.booker.id")
                .isEqualTo(new Long(booker.getId()).intValue());
        assertThat(result)
                .extractingJsonPathStringValue("$.booker.name")
                .isEqualTo(booker.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.booker.email")
                .isEqualTo(booker.getEmail());

        assertThat(result)
                .extractingJsonPathNumberValue("$.item.id")
                .isEqualTo(new Long(item.getId()).intValue());
        assertThat(result)
                .extractingJsonPathStringValue("$.item.name")
                .isEqualTo(item.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.item.description")
                .isEqualTo(item.getDescription());
        assertThat(result)
                .extractingJsonPathBooleanValue("$.item.available")
                .isEqualTo(item.getAvailable());
    }
}
