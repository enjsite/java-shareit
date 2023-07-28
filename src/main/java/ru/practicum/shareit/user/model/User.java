package ru.practicum.shareit.user.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}