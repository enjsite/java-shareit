package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void get() {
    }

    @Test
    void getAllUsers_whenInvoked_thenResponseStatusOkWithUsersInBody() {

        List<UserDto> expectedUsers = List.of(new UserDto());
        Mockito.when(userService.getAllUsers()).thenReturn(expectedUsers);

        ResponseEntity<List<UserDto>> response = ResponseEntity.ok(userController.getAllUsers());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsers, response.getBody());
    }

    @Test
    void saveNewUser() {
    }

    @Test
    void update() {
    }

    @Test
    void removeUserById() {
    }
}