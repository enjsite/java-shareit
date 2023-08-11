package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @PostMapping("")
    public ResponseEntity<Object> add(@RequestHeader("X-Sharer-User-Id") long userId,
                                      @Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Получен новый запрос.");
        return itemRequestClient.add(userId, itemRequestDto);
    }

    @GetMapping("")
    public ResponseEntity<Object> getByRequestor(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Посмотреть запросы пользователя.");
        return itemRequestClient.getByRequestorId(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long requestId) {
        log.info("Запрос на просмотр запроса с requestId " + requestId);
        return itemRequestClient.get(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestParam(required = false, defaultValue = "0") Integer from,
                                       @RequestParam(required = false, defaultValue = "100") Integer size) {
        log.info("Посмотреть запросы других пользователей.");
        return itemRequestClient.getAll(userId, from, size);
    }

}
