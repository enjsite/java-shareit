package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping("")
    public ItemRequestDto add(@RequestHeader("X-Sharer-User-Id") long userId,
                          @Valid @RequestBody ItemRequestDto itemRequestDto) {
        log.info("Получен новый запрос.");
        return itemRequestService.add(userId, itemRequestDto);
    }

    @GetMapping("")
    public List<ItemRequestDto> getByRequestor(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Посмотреть запросы пользователя.");
        return itemRequestService.getByRequestorId(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable long requestId) {
        log.info("Запрос на просмотр запроса с requestId " + requestId);
        return itemRequestService.get(userId, requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestParam(required = false) Integer from,
                                       @RequestParam(required = false) Integer size) throws ValidationException {
        log.info("Посмотреть запросы других пользователей.");
        return itemRequestService.getAll(userId, from, size);
    }
}
