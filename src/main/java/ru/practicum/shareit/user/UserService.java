package ru.practicum.shareit.user;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto get(long id);

    List<UserDto> getAllUsers();

    UserDto create(UserDto userDto) throws ValidationException;

    UserDto update(long id, UserDto userDto) throws ValidationException;

    void removeUserById(long id);
}
