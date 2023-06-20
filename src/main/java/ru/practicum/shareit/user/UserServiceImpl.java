package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User get(long id) {
        User user = userRepository.getReferenceById(id);
        if (user == null) {
            log.error("Не существует пользователя с id " + id);
            throw new NullPointerException("Не существует пользователя с id " + id);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        var newUser = userRepository.save(user);
        log.info("Создан новый пользователь с Id " + newUser.getId());
        return newUser;
    }

    @Override
    public User update(long id, User user) throws ValidationException {
        var updatedUser = userRepository.getReferenceById(id);
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }

        return userRepository.save(updatedUser);
    }

    @Override
    public void removeUserById(long id) {
        log.info("Удаление пользователя {}", id);
        userRepository.deleteById(id);
    }

    private void validateEmail(String email, long userId) throws ValidationException {
        var users = getAllUsers();
        for (User u: users) {
            if (u.getEmail().equals(email) && u.getId() != userId) {
                log.error("Дубликат email");
                throw new ValidationException("Дубликат email;");
            }
        }
    }

    private void validateEmail(String email) throws ValidationException {
        var users = getAllUsers();
        for (User u: users) {
            if (u.getEmail().equals(email)) {
                log.error("Дубликат email");
                throw new ValidationException("Дубликат email;");
            }
        }
    }
}
