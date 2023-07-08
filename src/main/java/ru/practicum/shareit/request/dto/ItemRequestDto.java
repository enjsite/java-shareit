package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
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
public class ItemRequestDto {

    private long id;

    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    private User requestor;

    private LocalDateTime created = LocalDateTime.now();

    private List<ItemForRequestDto> items = new ArrayList<>();

    public ItemRequestDto(long id, String description, User requestor, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.requestor = requestor;
        this.created = created;
    }
}
