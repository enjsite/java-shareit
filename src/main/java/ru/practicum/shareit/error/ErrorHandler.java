package ru.practicum.shareit.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

@Slf4j
@RestControllerAdvice("ru.practicum.shareit")
public class ErrorHandler {
    /*@ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(final ValidationException e) {
        log.error("Ошибка валидации: " + e.getMessage());
        return new ErrorResponse(
                "Ошибка валидации", e.getMessage()
        );
    }*/

    @ExceptionHandler({NullPointerException.class,
            NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(final RuntimeException e) {
        log.error("Объект не найден: " + e.getMessage());
        return new ErrorResponse(
                "Объект не найден", e.getMessage()
        );
    }

}
