package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public User get(long id) {
        User user = userDao.get(id);
        if (user == null) {
            log.error("Не существует пользователя с id " + id);
            throw new NullPointerException("Не существует пользователя с id " + id);
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User create(User user) throws ValidationException {
        validateEmail(user.getEmail());
        var newUser = userDao.create(user);
        log.info("Создан новый пользователь с Id " + newUser.getId());
        return newUser;
    }

    @Override
    public User update(long id, String name, String email) throws ValidationException {
        var curUser = get(id);
        validateEmail(email, id);
        var updatedUser = userDao.update(curUser, name, email);
        log.info("Пользователь " + updatedUser.getId() + " обновлен.");
        return updatedUser;
    }

    @Override
    public void removeUserById(long id) {
        log.info("Удаление пользователя {}", id);
        userDao.delete(id);
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
