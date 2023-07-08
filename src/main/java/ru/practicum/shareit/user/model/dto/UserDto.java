package ru.practicum.shareit.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class UserDto {

    private long id;

    @NotBlank(message = "Имя не должно быть пустым")
    private String name;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Указан невалидный email")
    private String email;
}
