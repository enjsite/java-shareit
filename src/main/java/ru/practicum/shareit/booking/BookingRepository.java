package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerOrderByStartDesc(User user);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long userId);

    @Query(value = " select b from Booking b " +
            "where b.booker = ?1 " +
            " and (?2 BETWEEN b.start AND b.end)" +
            " order by b.start desc")
    List<Booking> findAllByBookerCurrentOrderByStartDesc(User user, LocalDateTime curTime);

    @Query(value = " select b from Booking b " +
            "where b.booker = ?1 " +
            " and b.start > ?2" +
            " order by b.start desc")
    List<Booking> findAllByBookerFutureOrderByStartDesc(User user, LocalDateTime curTime);

    @Query(value = " select b from Booking b " +
            "where b.item.owner = ?1 " +
            " and (?2 BETWEEN b.start AND b.end)" +
            " order by b.start desc")
    List<Booking> findAllByItemOwnerIdCurrentOrderByStartDesc(User user, LocalDateTime curTime);

    @Query(value = " select b from Booking b " +
            "where b.item.owner = ?1 " +
            " and b.start > ?2" +
            " order by b.start desc")
    List<Booking> findAllByItemOwnerIdFutureOrderByStartDesc(User user, LocalDateTime curTime);

    Booking findTopByItemIdAndStartBeforeOrderByStartDesc(long id, LocalDateTime now);

    Booking findTopByItemIdAndStartAfterOrderByStartAsc(long id, LocalDateTime now);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long userId, BookingStatus rejected);

    List<Booking> findAllByBookerAndStatusOrderByStartDesc(User user, BookingStatus rejected);

    Booking findFirstByBookerAndItemAndEndBefore(User author, Item item, LocalDateTime now);

    List<Booking> findAllByBookerAndEndBeforeOrderByStartDesc(User user, LocalDateTime now);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now);
}
