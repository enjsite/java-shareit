package ru.practicum.shareit.user.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserDto {

    long id;

    @NotBlank(message = "Имя не должно быть пустым")
    String name;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Указан невалидный email")
    String email;
}
