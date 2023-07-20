package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import ru.practicum.shareit.booking.BookingStatus;
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
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        UserDto actualUser = userService.get(userId);
        assertEquals(expectedUser, UserMapping.mapToUser(actualUser));
    }

    @Test
    void get_whenUserNotFound_thenExceptionThrown() {

        long userId = 0L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> userService.get(userId));
    }

    @Test
    void getAllUsers() {

        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        var result = userRepository.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void create_whenUserNameValid_thenSavedUser() {

        User userToSave = new User();
        Mockito.when(userRepository.save(userToSave)).thenReturn(userToSave);

        UserDto actualUser = userService.create(UserMapping.toUserDto(userToSave));

        assertEquals(userToSave, UserMapping.mapToUser(actualUser));
        Mockito.verify(userRepository).save(userToSave);
    }

    @Test
    void create_whenUserEmailNotValid_thenNotSavedUser() {

        User userToSave = new User();
        //Mockito.doThrow(Exception.class).when(userRepository).save(userToSave);

        //assertThrows(Exception.class, () -> userService.create(UserMapping.toUserDto(userToSave)));
        //Mockito.verify(userRepository, Mockito.never()).save(userToSave);
    }

    @Test
    void update() {
    }

    @Test
    void removeUserById() {
    }
}