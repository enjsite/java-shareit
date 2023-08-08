package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private Item item;

    private User user;

    private User owner;

    @BeforeEach
    void setUp() {
        this.user = createUser("userName", "userEmail@mail.ru");
        this.owner = createUser("ownerUser", "ownerEmail@mail.ru");
        this.item = createItem("itemName", "itemDescription", true, owner);
    }

    private Item createItem(String name, String description, Boolean available, User owner) {
        var item = new Item(name, description, available);
        item.setOwner(owner);
        return itemRepository.save(item);
    }

    private User createUser(String name, String email) {
        var user = new User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    private Booking createBooking(BookingStatus status, Item item, User booker, LocalDateTime start, LocalDateTime end) {
        var booking = new Booking();
        booking.setStatus(status);
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStart(start);
        booking.setEnd(end);
        return bookingRepository.save(booking);
    }

    @Test
    void findAllByBookerOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now(), LocalDateTime.now());
        var result = bookingRepository.findAllByBookerOrderByStartDesc(user, Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getBooker(), result.toList().get(0).getBooker());
    }

    @Test
    void findAllByItemOwnerIdOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now(), LocalDateTime.now());
        var result = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(owner.getId(), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getItem().getOwner().getId(), result.toList().get(0).getItem().getOwner().getId());
    }

    @Test
    void findAllByBookerCurrentOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        var result = bookingRepository.findAllByBookerCurrentOrderByStartDesc(user, LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getBooker(), result.toList().get(0).getBooker());
    }

    @Test
    void findAllByBookerFutureOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByBookerFutureOrderByStartDesc(user, LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getBooker(), result.toList().get(0).getBooker());
    }

    @Test
    void findAllByItemOwnerIdCurrentOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByItemOwnerIdCurrentOrderByStartDesc(owner, LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getItem().getOwner(), result.toList().get(0).getItem().getOwner());
    }

    @Test
    void findAllByItemOwnerIdFutureOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByItemOwnerIdFutureOrderByStartDesc(owner, LocalDateTime.now(), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getItem().getOwner(), result.toList().get(0).getItem().getOwner());
    }

    @Test
    void findTopByItemIdAndStartBeforeOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findTopByItemIdAndStartBeforeOrderByStartDesc(item.getId(), LocalDateTime.now());
        assertNotNull(result);
        assertEquals(booking.getId(), result.getId());
        assertEquals(booking.getItem().getId(), result.getItem().getId());
    }

    @Test
    void findTopByItemIdAndStartAfterOrderByStartAsc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findTopByItemIdAndStartAfterOrderByStartAsc(item.getId(), LocalDateTime.now());
        assertNotNull(result);
        assertEquals(booking.getId(), result.getId());
        assertEquals(booking.getItem().getId(), result.getItem().getId());
    }

    @Test
    void findAllByItemOwnerIdAndStatusOrderByStartDesc() {
        var booking = createBooking(BookingStatus.REJECTED, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(owner.getId(), BookingStatus.REJECTED, Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getStatus(), result.toList().get(0).getStatus());
        assertEquals(booking.getItem().getOwner().getId(), result.toList().get(0).getItem().getOwner().getId());
    }

    @Test
    void findAllByBookerAndStatusOrderByStartDesc() {
        var booking = createBooking(BookingStatus.REJECTED, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByBookerAndStatusOrderByStartDesc(user, BookingStatus.REJECTED, Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getStatus(), result.toList().get(0).getStatus());
        assertEquals(booking.getBooker(), result.toList().get(0).getBooker());
    }

    @Test
    void findFirstByBookerAndItemAndEndBefore() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(2));
        var result = bookingRepository.findFirstByBookerAndItemAndEndBefore(user, item, LocalDateTime.now());
        assertNotNull(result);
        assertEquals(booking.getId(), result.getId());
        assertEquals(booking.getItem().getId(), result.getItem().getId());
        assertEquals(booking.getBooker(), result.getBooker());
    }

    @Test
    void findAllByBookerAndEndBeforeOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByBookerAndEndBeforeOrderByStartDesc(user, LocalDateTime.now().plusDays(4), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getBooker(), result.toList().get(0).getBooker());
    }

    @Test
    void findAllByItemOwnerIdAndEndBeforeOrderByStartDesc() {
        var booking = createBooking(BookingStatus.WAITING, item, user, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3));
        var result = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(owner.getId(), LocalDateTime.now().plusDays(4), Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getSize());
        assertEquals(booking.getId(), result.toList().get(0).getId());
        assertEquals(booking.getItem().getOwner().getId(), result.toList().get(0).getItem().getOwner().getId());
    }
}