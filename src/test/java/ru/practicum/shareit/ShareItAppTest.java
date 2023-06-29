package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShareItAppTest {

    @Test
    void testMain() {

        Assertions.assertDoesNotThrow(ShareItApp::new);
        Assertions.assertDoesNotThrow(() -> ShareItApp.main(new String[]{}));
    }
}