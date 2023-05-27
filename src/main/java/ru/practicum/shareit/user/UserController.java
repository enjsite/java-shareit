package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

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
    public User get(@PathVariable Integer id) throws ValidationException {
        log.info("Запрос на получение юзера с id " + id);
        return userService.get(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User saveNewUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос на создание нового пользователя.");
        return userService.create(user);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Integer id,
                       @RequestBody User user) throws ValidationException {
        log.info("Получен запрос на апдейт юзера с id " + id + ", name " + user.getName() + " email " + user.getEmail());
        return userService.update(id, user.getName(), user.getEmail());
    }

    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable Integer id) {
        log.info("Запрос на удаление пользователя {}", id);
        userService.removeUserById(id);
    }

}
