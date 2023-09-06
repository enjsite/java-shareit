package ru.practicum.shareit.request;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto add(long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto>  getByRequestorId(long userId);

    ItemRequestDto get(long userId, long requestId);

    List<ItemRequestDto> getAll(long userId, Integer from, Integer size) throws ValidationException;
}
