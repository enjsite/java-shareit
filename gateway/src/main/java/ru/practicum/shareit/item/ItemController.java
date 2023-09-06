package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;

import javax.validation.Valid;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Integer itemId) {
        log.info(String.format("Запрос на получение вещи с itemId %d", itemId));
        return itemClient.get(userId, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemsByUserId(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info(String.format("Запрос на получение вещей пользователя %d", userId));
        return itemClient.getItems(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> get(@RequestHeader("X-Sharer-User-Id") long userId, @RequestParam String text) {
        log.info(String.format("Запрос на поиск вещи по названию или описанию: %s", text));
        return itemClient.search(userId, text);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader("X-Sharer-User-Id") long userId,
                    @Valid @RequestBody ItemDto itemDto) {
        log.info(String.format("Получен запрос на создание новой вещи от пользователя %d", userId));
        return itemClient.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                       @PathVariable Integer itemId,
                       @RequestBody ItemDto itemDto) {
        log.info(String.format("Получен запрос на апдейт вещи с id %d", itemId));
        return itemClient.update(itemId, itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable long itemId) {
        itemClient.deleteItem(userId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable Integer itemId,
                                 @RequestBody CommentDto comment) {
        log.info(String.format("Получен запрос на добавление комментария к вещи с id %d", itemId));
        return itemClient.addComment(userId, itemId, comment);
    }

}
