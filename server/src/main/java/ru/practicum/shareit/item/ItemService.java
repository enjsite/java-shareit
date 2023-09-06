package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;

import java.util.List;

public interface ItemService {

    ItemDto addNewItem(long userId, ItemDto itemDto);

    ItemForOwnerDto get(long userId, long itemId);

    ItemDto update(long itemId, ItemDto itemDto, long userId);

    List<ItemForOwnerDto> getItems(long userId);

    List<ItemDto> search(long userId, String text);

    void deleteItem(long userId, long itemId);

    CommentDto addComment(long userId, long itemId, Comment comment) throws NotAvailableException;
}
