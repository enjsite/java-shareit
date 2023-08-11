package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.dto.UserDto;

import javax.validation.Valid;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id) {
        log.info("Запрос на получение юзера с id " + id);
        return userClient.get(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userClient.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> saveNewUser(@RequestBody @Valid UserDto userDto) {
        log.info("Получен запрос на создание нового пользователя.");
        return userClient.create(userDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                       @RequestBody UserDto userDto) {
        log.info("Получен запрос на апдейт юзера с id " + id);
        return userClient.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable Integer id) {
        log.info("Запрос на удаление пользователя {}", id);
        userClient.removeUserById(id);
    }

}
