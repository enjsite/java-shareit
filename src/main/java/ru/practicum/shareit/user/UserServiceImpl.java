package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapping;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto get(long id) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return UserMapping.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapping::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto userDto) {
        var newUser = userRepository.save(UserMapping.mapToUser(userDto));
        log.info("Создан новый пользователь с Id " + newUser.getId());
        return UserMapping.toUserDto(newUser);
    }

    @Override
    public UserDto update(long id, UserDto userDto) throws ValidationException {
        var updatedUser = userRepository.getReferenceById(id);
        if (userDto.getName() != null) {
            updatedUser.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            updatedUser.setEmail(userDto.getEmail());
        }

        return UserMapping.toUserDto(userRepository.save(updatedUser));
    }

    @Override
    public void removeUserById(long id) {
        log.info("Удаление пользователя {}", id);
        userRepository.deleteById(id);
    }

    private void validateEmail(String email, long userId) throws ValidationException {
        var users = getAllUsers();
        for (UserDto u: users) {
            if (u.getEmail().equals(email) && u.getId() != userId) {
                log.error("Дубликат email");
                throw new ValidationException("Дубликат email;");
            }
        }
    }

    private void validateEmail(String email) throws ValidationException {
        var users = getAllUsers();
        for (UserDto u: users) {
            if (u.getEmail().equals(email)) {
                log.error("Дубликат email");
                throw new ValidationException("Дубликат email;");
            }
        }
    }
}
