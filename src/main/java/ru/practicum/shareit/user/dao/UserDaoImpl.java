package ru.practicum.shareit.user.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    private HashMap<Long, User> users = new HashMap<>();
    private long genId = 0;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User get(long userId) {
        if (!users.containsKey(userId)) {
            return null;
        }
        return users.get(userId);
    }

    @Override
    public User create(User user) {
        genId++;
        if (user.getId() == 0) {
            user.setId(genId);
        }
        users.put(user.getId(), user);
        log.info("User c id " + user.getId() + " добавлен.");
        return user;
    }

    @Override
    public User update(User curUser, String name, String email) {
        if (name != null) {
            curUser.setName(name);
        }
        if (email != null) {
            curUser.setEmail(email);
        }
        users.put(curUser.getId(), curUser);
        log.info("User c id " + curUser.getId() + " обновлен.");
        return curUser;
    }

    @Override
    public void delete(long userId) {
        log.debug("Пользователь с идентификатором {} удалён.", userId);
        users.remove(userId);
    }

}
