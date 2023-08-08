package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;

import javax.validation.Valid;
import java.util.List;

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
        log.info(String.format("Запрос на получение вещи с itemId %d", itemId));
        return itemService.get(userId, itemId);
    }

    @GetMapping
    public List<ItemForOwnerDto> getAllItemsByUserId(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info(String.format("Запрос на получение вещей пользователя %d", userId));
        return itemService.getItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> get(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String text) {
        log.info(String.format("Запрос на поиск вещи по названию или описанию: %s", text));
        return itemService.search(userId, text);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") long userId,
                    @Valid @RequestBody ItemDto itemDto) {
        log.info(String.format("Получен запрос на создание новой вещи от пользователя %d", userId));
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable Integer itemId,
                       @RequestBody ItemDto itemDto) throws ValidationException {
        log.info(String.format("Получен запрос на апдейт вещи с id %d", itemId));
        return itemService.update(itemId, itemDto, userId);
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
        log.info(String.format("Получен запрос на добавление комментария к вещи с id %d", itemId));
        return itemService.addComment(userId, itemId, comment);
    }
}
