package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    private ItemRequestServiceImpl itemRequestService;

    private User user;

    private ItemRequest itemRequest;

    @BeforeEach
    void setUp() {

        itemRequestService = new ItemRequestServiceImpl(itemRequestRepository, userRepository, itemRepository);
        user = new User(1L, "userName", "userEmail@mail.ru");
        itemRequest = new ItemRequest(2L, "descr", user, LocalDateTime.now());
    }

    @Test
    void add() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRequestRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("descr");
        itemRequestDto.setCreated(LocalDateTime.now());

        var result = itemRequestService.add(user.getId(), itemRequestDto);
        assertEquals(itemRequestDto, result);
        verify(itemRequestRepository, times(1)).save(any());
    }

    @Test
    void getByRequestorId() {
    }

    @Test
    void get() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(itemRequest.getId())).thenReturn(Optional.of(itemRequest));

        var result = itemRequestService.get(user.getId(), itemRequest.getId());
        assertNotNull(result);
        assertEquals(ItemRequestMapper.toItemRequestDto(itemRequest), result);
    }

    @Test
    void getAll() {
    }
}