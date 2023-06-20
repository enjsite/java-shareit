package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
}