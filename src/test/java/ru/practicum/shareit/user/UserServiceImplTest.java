package ru.practicum.shareit.user;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapping;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void get_whenUserFound_thenReturnedUser() {

        long userId = 0L;
        User expectedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        UserDto actualUser = userService.get(userId);
        assertEquals(expectedUser, UserMapping.mapToUser(actualUser));
    }

    @Test
    void get_whenUserNotFound_thenExceptionThrown() {

        long userId = 0L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> userService.get(userId));
    }

    @Test
    void getAllUsers() {

        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        var result = userService.getAllUsers();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void create_whenUserNameValid_thenSavedUser() {

        User userToSave = new User();
        when(userRepository.save(userToSave)).thenReturn(userToSave);

        UserDto actualUser = userService.create(UserMapping.toUserDto(userToSave));

        assertEquals(userToSave, UserMapping.mapToUser(actualUser));
        verify(userRepository).save(userToSave);
    }

    @SneakyThrows
    @Test
    void update() {

        long userId = 1L;
        User updatedUser = new User(1L, "userName", "userEmail@mail.ru");
        UserDto newUserDto = new UserDto();
        newUserDto.setId(1L);
        newUserDto.setName("newUserName");
        newUserDto.setEmail("newUserEmail@mail.ru");

        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.getReferenceById(userId)).thenReturn(updatedUser);
        UserDto actualUser = userService.update(userId, newUserDto);

        assertEquals(newUserDto.getName(), actualUser.getName());
    }

    @Test
    void removeUserById() {
    }
}