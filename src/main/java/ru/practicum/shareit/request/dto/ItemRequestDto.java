package ru.practicum.shareit.request.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.dto.ItemForRequestDto;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class ItemRequestDto {

    long id;

    @NotBlank(message = "Описание не должно быть пустым")
    String description;

    User requestor;

    LocalDateTime created = LocalDateTime.now();

    List<ItemForRequestDto> items = new ArrayList<>();

    public ItemRequestDto(long id, String description, User requestor, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }
}
