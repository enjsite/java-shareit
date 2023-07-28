package ru.practicum.shareit.item.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Отзыв не должен быть пустым")
    @Column(name = "text")
    String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    User author;

    @Column(name = "created")
    LocalDateTime created = LocalDateTime.now();
}
