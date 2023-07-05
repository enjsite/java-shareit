package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotSupportedException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private BookingServiceImpl bookingService;

    private BookingRepository bookingRepository;

    private UserRepository userRepository;

    private ItemRepository itemRepository;

    private User user;

    private User owner;

    private Item item;

    private Booking booking;

    private Pageable pageable = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "start"));

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        userRepository = mock(UserRepository.class);
        itemRepository = mock(ItemRepository.class);
        bookingService = new BookingServiceImpl(bookingRepository, itemRepository, userRepository);
        user = new User(1L, "userName", "userEmail@mail.ru");

        owner = new User(2L, "ownerUser", "ownerEmail@mail.ru");
        item = new Item(3L, "itemName", "itemDescription", true);
        item.setOwner(owner);
        booking = new Booking(4L, item, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(3), user, BookingStatus.WAITING);

        now = LocalDateTime.now();
    }

    @Test
    void add() throws ValidationException, NotAvailableException {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(bookingRepository.save(booking)).thenReturn(booking);
        var result = bookingService.add(user.getId(), BookingMapper.toBookingDto(booking));

        assertEquals(BookingMapper.toBookingDto(booking), result);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void approved() throws ValidationException {
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(bookingRepository.findById(4L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        var result = bookingService.approved(owner.getId(), booking.getId(), true);
        assertEquals(booking.getStatus(), result.getStatus());
        assertEquals(BookingStatus.APPROVED, result.getStatus());
    }

    @Test
    void notApproved() throws ValidationException {
        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(bookingRepository.findById(4L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        var result = bookingService.approved(owner.getId(), booking.getId(), false);
        assertEquals(booking.getStatus(), result.getStatus());
        assertEquals(BookingStatus.REJECTED, result.getStatus());
    }

    @Test
    void get() throws NotAvailableException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findById(4L)).thenReturn(Optional.of(booking));
        var result = bookingService.get(user.getId(), 4L);
        assertNotNull(result);
        assertEquals(BookingMapper.toBookingDto(booking), result);
        verify(bookingRepository, times(1)).findById(4L);
    }

    @Test
    void getAllByBooker() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerOrderByStartDesc(user, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAll(1L, BookingStatus.ALL.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByBookerOrderByStartDesc(user, pageable);
    }

    @Test
    void getAllCurrentByBooker() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerCurrentOrderByStartDesc(user, now, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAll(1L, BookingStatus.CURRENT.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByBookerCurrentOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllFutureByBooker() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerFutureOrderByStartDesc(user, now, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAll(1L, BookingStatus.FUTURE.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByBookerFutureOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllPastByBooker() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerAndEndBeforeOrderByStartDesc(user, now, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAll(1L, BookingStatus.PAST.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByBookerAndEndBeforeOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllRejectedByBooker() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerAndStatusOrderByStartDesc(user, BookingStatus.REJECTED, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAll(1L, BookingStatus.REJECTED.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByBookerAndStatusOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllWaitingByBooker() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByBookerAndStatusOrderByStartDesc(user, BookingStatus.WAITING, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAll(1L, BookingStatus.WAITING.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByBookerAndStatusOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllByOwner() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(user.getId(), pageable)).thenReturn(Page.empty());
        var result = bookingService.getAllForOwner(1L, BookingStatus.ALL.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByItemOwnerIdOrderByStartDesc(user.getId(), pageable);
    }

    @Test
    void getAllCurrentByOwner() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdCurrentOrderByStartDesc(user, now, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAllForOwner(1L, BookingStatus.CURRENT.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByItemOwnerIdCurrentOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllFutureByOwner() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdFutureOrderByStartDesc(user, now, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAllForOwner(1L, BookingStatus.FUTURE.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByItemOwnerIdFutureOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllPastByOwner() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(user.getId(), now, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAllForOwner(1L, BookingStatus.PAST.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        //verify(bookingRepository, times(1)).findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllRejectedByOwner() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(user.getId(), BookingStatus.REJECTED, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAllForOwner(1L, BookingStatus.REJECTED.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        //verify(bookingRepository, times(1)).findAllByItemOwnerIdAndStatusOrderByStartDesc(any(), any(), any());
    }

    @Test
    void getAllWaitingByOwner() throws ValidationException, NotSupportedException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(user.getId(), BookingStatus.WAITING, pageable)).thenReturn(Page.empty());
        var result = bookingService.getAllForOwner(1L, BookingStatus.WAITING.toString(), 0, 100);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        //verify(bookingRepository, times(3)).findAllByItemOwnerIdAndStatusOrderByStartDesc(any(), any(), any());
    }

}