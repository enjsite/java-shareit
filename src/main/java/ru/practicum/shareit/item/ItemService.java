package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item addNewItem(long userId, Item item);
    Item get(long userId, long itemId);
    Item update(long itemId, Item item, long userId);
    List<Item> getItems(long userId);
    List<Item> search(long userId, String text);
    void deleteItem(long userId, long itemId);
}
