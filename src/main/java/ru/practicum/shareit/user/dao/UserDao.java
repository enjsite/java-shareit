package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
    //User save(User user);

    User get(long userId);

    User create(User user);

    User update(User curUser, String name, String email);

    void delete(long userId);
}
