package ru.practicum.shareit.item.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class ItemForRequestDto {

    long id;

    String name;

    long ownerId;

    String description;

    Boolean available;

    Long requestId;
}
