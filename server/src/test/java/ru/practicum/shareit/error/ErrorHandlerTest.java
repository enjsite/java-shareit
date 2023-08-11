package ru.practicum.shareit.error;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotSupportedException;
import ru.practicum.shareit.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {

    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void handleStatus_NOT_FOUND_test() {

        var ex = new NotFoundException("Объект не найден");
        var result = errorHandler.handle(ex);
        assertNotNull(result);
        assertEquals(ex.getMessage(), result.getError());
    }

    @Test
    void handleStatus_BAD_REQUEST_ValidationExceptionTest() {

        var ex = new ValidationException("Объект не найден");
        var result = errorHandler.handle(ex);
        assertNotNull(result);
        assertEquals(ex.getMessage(), result.getError());
    }

    @Test
    void handleStatus_BAD_REQUEST_NotAvailableExceptionTest() {

        var ex = new NotAvailableException("Объект не найден");
        var result = errorHandler.handle(ex);
        assertNotNull(result);
        assertEquals(ex.getMessage(), result.getError());
    }

    @Test
    void handleStatus_INTERNAL_SERVER_ERROR_test() {

        var ex = new NotSupportedException("Unknown state: UNSUPPORTED_STATUS");
        var result = errorHandler.handle(ex);
        assertNotNull(result);
        assertEquals(ex.getMessage(), result.getError());
    }
}