package ru.practicum.shareit.item.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class ItemDto {

    long id;

    @NotBlank(message = "Имя не должно быть пустым")
    String name;

    @NotBlank(message = "Описание не должно быть пустым")
    String description;

    @NotNull(message = "Доступность должна быть указана")
    Boolean available;

    User owner;

    Long requestId;

    List<CommentDto> comments = new ArrayList<>();

    public ItemDto(long id, String name, String description, Boolean available, User owner, Long requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.owner = owner;
        this.requestId = requestId;
    }
}
