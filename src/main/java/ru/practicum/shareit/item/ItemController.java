package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapping;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ItemForOwnerDto get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Integer itemId) {
        log.info("Запрос на получение вещи с itemId " + itemId);
        return itemService.get(userId, itemId);
    }

    @GetMapping
    public List<ItemForOwnerDto> getAllItemsByUserId(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Запрос на получение вещей пользователя " + userId);
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> get(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String text) {
        log.info("Запрос на поиск вещи по названию или описанию " + text);
        return itemService.search(userId, text).stream()
                .map(ItemMapping::toItemDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public Item add(@RequestHeader("X-Sharer-User-Id") long userId,
                    @Valid @RequestBody Item item) {
        log.info("Получен запрос на создание новой вещи: " + item + " от пользователя " + userId);
        return itemService.addNewItem(userId, item);
    }

    @PatchMapping("/{itemId}")
    public Item update(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable Integer itemId,
                       @RequestBody Item item) throws ValidationException {
        log.info("Получен запрос на апдейт вещи с id " + itemId);
        return itemService.update(itemId, item, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable Integer itemId,
                                 @RequestBody Comment comment) throws NotAvailableException {
        log.info("Получен запрос на добавление комментария к вещи с id " + itemId);
        return itemService.addComment(userId, itemId, comment);
    }
}
