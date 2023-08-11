package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapping;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ItemRequestServiceImpl implements ItemRequestService {

    ItemRequestRepository itemRequestRepository;
    UserRepository userRepository;
    ItemRepository itemRepository;

    @Override
    public ItemRequestDto add(long userId, ItemRequestDto itemRequestDto) {

        User requestor = userRepository.findById(userId).orElseThrow();
        itemRequestDto.setRequestor(requestor);
        ItemRequest itemRequest = ItemRequestMapper.mapToItemRequest(itemRequestDto);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDto> getByRequestorId(long userId) {

        User requestor = userRepository.findById(userId).orElseThrow();
        List<ItemRequest> requests = itemRequestRepository.findAllByRequestor(requestor);
        return requests.stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .map(this::setItemsForRequest)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto get(long userId, long requestId) {
        User user = userRepository.findById(userId).orElseThrow();
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow();
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
        itemRequestDto = setItemsForRequest(itemRequestDto);
        return itemRequestDto;
    }

    @Override
    public List<ItemRequestDto> getAll(long userId, Integer from, Integer size) throws ValidationException {

        User user = userRepository.findById(userId).orElseThrow();
        if (from < 0 || size < 1) {
            throw new ValidationException(String.format("Недопустимые значения пагинации: параметр from %s (не может быть меньше 0) и параметр size %s (не может быть меньше 1)", from, size));
        }
        Pageable pageable = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "created"));
        Page<ItemRequest> itemRequests = itemRequestRepository.findAllByRequestorIdNot(userId, pageable);
        return itemRequests.stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .map(this::setItemsForRequest)
                .collect(Collectors.toList());
    }

    private ItemRequestDto setItemsForRequest(ItemRequestDto itemRequestDto) {
        List<Item> items = itemRepository.findAllByRequestId(itemRequestDto.getId());
        itemRequestDto.setItems(items.stream().map(ItemMapping::toItemForRequestDto).collect(Collectors.toList()));
        return itemRequestDto;
    }
}