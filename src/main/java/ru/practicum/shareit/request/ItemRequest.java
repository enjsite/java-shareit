package ru.practicum.shareit.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "description")
    @NotBlank(message = "Описание не должно быть пустым")
    String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestor_id")
    User requestor;

    @Column(name = "created")
    LocalDateTime created;
}
