package ru.practicum.shareit.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemForRequestDto {

    private long id;

    private String name;

    private long ownerId;

    private String description;

    private Boolean available;

    private Long requestId;
}
