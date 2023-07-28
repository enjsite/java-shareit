package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Entity
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "available")
    Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id")
    ItemRequest request;

    public Item(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Item(Long id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
