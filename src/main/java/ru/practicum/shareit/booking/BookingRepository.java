package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Component
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByBookerOrderByStartDesc(User user, Pageable pageable);

    Page<Booking> findAllByItemOwnerIdOrderByStartDesc(long userId, Pageable pageable);

    @Query(value = " select b from Booking b " +
            "where b.booker = ?1 " +
            " and (?2 BETWEEN b.start AND b.end)" +
            " order by b.start desc")
    Page<Booking> findAllByBookerCurrentOrderByStartDesc(User user, LocalDateTime curTime, Pageable pageable);

    @Query(value = " select b from Booking b " +
            "where b.booker = ?1 " +
            " and b.start > ?2" +
            " order by b.start desc")
    Page<Booking> findAllByBookerFutureOrderByStartDesc(User user, LocalDateTime curTime, Pageable pageable);

    @Query(value = " select b from Booking b " +
            "where b.item.owner = ?1 " +
            " and (?2 BETWEEN b.start AND b.end)" +
            " order by b.start desc")
    Page<Booking> findAllByItemOwnerIdCurrentOrderByStartDesc(User user, LocalDateTime curTime, Pageable pageable);

    @Query(value = " select b from Booking b " +
            "where b.item.owner = ?1 " +
            " and b.start > ?2" +
            " order by b.start desc")
    Page<Booking> findAllByItemOwnerIdFutureOrderByStartDesc(User user, LocalDateTime curTime, Pageable pageable);

    Booking findTopByItemIdAndStartBeforeOrderByStartDesc(long id, LocalDateTime now);

    Booking findTopByItemIdAndStartAfterOrderByStartAsc(long id, LocalDateTime now);

    Page<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(long userId, BookingStatus status, Pageable pageable);

    Page<Booking> findAllByBookerAndStatusOrderByStartDesc(User user, BookingStatus status, Pageable pageable);

    Booking findFirstByBookerAndItemAndEndBefore(User author, Item item, LocalDateTime now);

    Page<Booking> findAllByBookerAndEndBeforeOrderByStartDesc(User user, LocalDateTime now, Pageable pageable);

    Page<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime now, Pageable pageable);
}
