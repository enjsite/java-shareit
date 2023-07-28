package ru.practicum.shareit.booking;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
@Generated
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    Item item;

    @Column(name = "start_date")
    LocalDateTime start;

    @Column(name = "end_date")
    LocalDateTime end;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "booker_id")
    User booker;

    @Enumerated(EnumType.STRING)
    BookingStatus status = BookingStatus.WAITING;

}
