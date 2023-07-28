package ru.practicum.shareit.item.comment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class CommentDto {

    long id;

    String text;

    String authorName;

    String created;
}
