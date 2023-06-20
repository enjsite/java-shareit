package ru.practicum.shareit.item;

import ru.practicum.shareit.exception.NotAvailableException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemForOwnerDto;

import java.util.List;

public interface ItemService {

    Item addNewItem(long userId, Item item);

    ItemForOwnerDto get(long userId, long itemId);

    Item update(long itemId, Item item, long userId);

    List<ItemForOwnerDto> getItems(long userId);

    List<Item> search(long userId, String text);

    void deleteItem(long userId, long itemId);

    CommentDto addComment(long userId, long itemId, Comment comment) throws NotAvailableException;
}
