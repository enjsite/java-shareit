package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentMapper;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapping;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto addNewItem(long userId, ItemDto itemDto) {
        User owner = userRepository.findById(userId).orElseThrow();
        itemDto.setOwner(owner);
        Item item = ItemMapping.mapToItem(itemDto, owner);
        return ItemMapping.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemForOwnerDto get(long userId, long itemId) {
        Item item = itemRepository.getReferenceById(itemId);
        if (item == null) {
            log.error("Не существует вещи с id " + itemId);
            throw new NullPointerException("Не существует вещи с id " + itemId);
        }
        var itemForOwner = ItemMapping.toItemForOwnerDto(item);
        if (item.getOwner().getId() == userId) {
            setBookingsForItem(itemForOwner);
        }
        setCommentsForItem(itemForOwner);

        return itemForOwner;
    }

    private ItemForOwnerDto setBookingsForItem(ItemForOwnerDto itemForOwnerDto) {

        Booking lastBooking = bookingRepository.findTopByItemIdAndStartBeforeOrderByStartDesc(itemForOwnerDto.getId(), LocalDateTime.now());
        if (lastBooking != null) {
            itemForOwnerDto.setLastBooking(BookingMapper.toBookingDtoForItems(lastBooking));

            Booking nextBooking = bookingRepository.findTopByItemIdAndStartAfterOrderByStartAsc(itemForOwnerDto.getId(), lastBooking.getEnd());
            if (nextBooking != null) {
                itemForOwnerDto.setNextBooking(BookingMapper.toBookingDtoForItems(nextBooking));
            }
        }
        return itemForOwnerDto;
    }

    private void setCommentsForItem(ItemForOwnerDto itemForOwnerDto) {

        List<Comment> comments = commentRepository.findAllByItemIdOrderByIdAsc(itemForOwnerDto.getId());
        itemForOwnerDto.setComments(comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList()));
    }

    @Override
    public ItemDto update(long itemId, ItemDto itemDto, long userId) {

        var curUser = userRepository.findById(userId).orElseThrow();
        var updatedItem = itemRepository.findByIdAndOwnerId(itemId, userId).orElseThrow();

        if (itemDto.getName() != null) {
            updatedItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            updatedItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            updatedItem.setAvailable(itemDto.getAvailable());
        }

        return ItemMapping.toItemDto(itemRepository.save(updatedItem));
    }

    @Override
    public List<ItemForOwnerDto> getItems(long userId) {

        return itemRepository.findAllByOwnerIdOrderById(userId).stream()
                .map(ItemMapping::toItemForOwnerDto).map(this::setBookingsForItem)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(long userId, String text) {
        return text.isEmpty() ? new ArrayList<ItemDto>() :
                itemRepository.search(text).stream().map(ItemMapping::toItemDto).collect(Collectors.toList());
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        var curUser = userService.get(userId);
        var curItem = get(userId, itemId);
        if (userId != curItem.getOwner().getId()) {
            throw new NotFoundException("Пользователь не является владельцем вещи.");
        }
        itemRepository.deleteById(itemId);
        log.info("Вещь " + itemId + " удалена.");
    }

    @Override
    public CommentDto addComment(long userId, long itemId, Comment comment) throws NotAvailableException {

        Item item = itemRepository.findById(itemId).orElseThrow();
        User author = userRepository.findById(userId).orElseThrow();

        Booking booking = bookingRepository.findFirstByBookerAndItemAndEndBefore(author, item, LocalDateTime.now());
        if (booking == null) {
            throw new NotAvailableException("Завершенное бронирование не найдено.");
        }

        comment.setItem(item);
        comment.setAuthor(author);

        commentRepository.save(comment);

        return CommentMapper.toCommentDto(comment);
    }


}
