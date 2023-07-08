package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
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
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description")
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestor_id")
    private User requestor;

    @Column(name = "created")
    private LocalDateTime created;
}
