package ru.practicum.shareit.item;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapping;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    private ItemServiceImpl itemService;

    private User user;

    private User owner;

    private Item item;

    private Booking booking;

    @BeforeEach
    void setUp() {

        itemService = new ItemServiceImpl(itemRepository, userRepository, bookingRepository, commentRepository, itemRequestRepository);
        user = new User(1L, "userName", "userEmail@mail.ru");
        owner = new User(2L, "ownerUser", "ownerEmail@mail.ru");
        item = new Item(3L, "itemName", "itemDescription", true, owner, null);
        booking = new Booking(4L, item, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(3), user, BookingStatus.WAITING);
    }

    @Test
    void addNewItem() {

        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(itemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var itemDto = new ItemDto(3L, "itemName", "itemDescription", true, owner, null);
        var result = itemService.addNewItem(owner.getId(), itemDto);
        assertEquals(itemDto, result);
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void get() {

        when(itemRepository.getReferenceById(3L)).thenReturn(item);
        var result = itemService.get(owner.getId(), item.getId());

        assertNotNull(result);
        assertEquals(ItemMapping.toItemForOwnerDto(item), result);
    }

    @Test
    void update() {

        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(itemRepository.findByIdAndOwnerId(item.getId(), owner.getId())).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var newItemDto = new ItemDto(3L, "updatedItemName", "updatedItemDescription", true, owner, null);

        var result = itemService.update(item.getId(), newItemDto, owner.getId());
        assertEquals(newItemDto.getName(), result.getName());
        assertEquals(newItemDto.getDescription(), result.getDescription());
        assertTrue(result.getAvailable());
    }

    @Test
    void getItems() {

        when(itemRepository.findAllByOwnerIdOrderById(owner.getId())).thenReturn(new ArrayList<>());
        var result = itemService.getItems(owner.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRepository, times(1)).findAllByOwnerIdOrderById(owner.getId());
    }

    @Test
    void search() {

        var text = "exampleText";
        when(itemRepository.search(text)).thenReturn(new ArrayList<>());
        var result = itemService.search(user.getId(), text);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itemRepository, times(1)).search(text);
    }

    @SneakyThrows
    @Test
    void addComment() {

        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findFirstByBookerAndItemAndEndBefore(any(), any(), any())).thenReturn(booking);

        var comment = new Comment();
        var result = itemService.addComment(user.getId(), item.getId(), comment);

        assertNotNull(result);
        assertEquals(user.getName(), result.getAuthorName());
    }

}