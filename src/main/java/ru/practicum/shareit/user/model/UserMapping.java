package ru.practicum.shareit.user.model;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.dto.UserDto;

@Service
public class UserMapping {

    public static UserDto toUserDto(User user) {

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }
}
