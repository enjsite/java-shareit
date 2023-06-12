package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemDao {

    Item get(long itemId);

    List<Item> findByUserId(long userId);

    Item create(long userId, Item item);

    Item update(Item curItem, Item newItem);

    List<Item> search(String text);

    void delete(long itemId);

}
