package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    User get(long id);
    List<User> getAllUsers();
    User create(User user) throws ValidationException;
    User update(long id, String name, String email) throws ValidationException;
    void removeUserById(long id);
}
