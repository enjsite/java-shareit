package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Integer id) throws ValidationException {
        log.info("Запрос на получение юзера с id " + id);
        return userService.get(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDto saveNewUser(@Valid @RequestBody UserDto userDto) throws ValidationException {
        log.info("Получен запрос на создание нового пользователя.");
        return userService.create(userDto);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable Integer id,
                       @RequestBody UserDto userDto) throws ValidationException {
        log.info("Получен запрос на апдейт юзера с id " + id);
        return userService.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable Integer id) {
        log.info("Запрос на удаление пользователя {}", id);
        userService.removeUserById(id);
    }

}
