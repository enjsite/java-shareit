package ru.practicum.shareit.request;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRequestRepository.findAllByRequestor(user)).thenReturn(new ArrayList<>());
        var result = itemRequestService.getByRequestorId(user.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRequestRepository, times(1)).findAllByRequestor(user);
    }

    @Test
    void get() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRequestRepository.findById(itemRequest.getId())).thenReturn(Optional.of(itemRequest));

        var result = itemRequestService.get(user.getId(), itemRequest.getId());
        assertNotNull(result);
        assertEquals(ItemRequestMapper.toItemRequestDto(itemRequest), result);
    }

    @SneakyThrows
    @Test
    void getAll() {

        Pageable pageable = PageRequest.of(0 / 100, 100, Sort.by(Sort.Direction.ASC, "created"));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRequestRepository.findAllByRequestorIdNot(user.getId(), pageable)).thenReturn(Page.empty());

        var result = itemRequestService.getAll(user.getId(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRequestRepository, times(1)).findAllByRequestorIdNot(user.getId(), pageable);
    }
}