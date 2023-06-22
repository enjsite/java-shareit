package ru.practicum.shareit.item.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class ItemDaoImpl implements ItemDao {

    private HashMap<Long, Item> items = new HashMap<>();
    private long genId = 0;

    @Override
    public Item get(long itemId) {
        if (!items.containsKey(itemId)) {
            return null;
        }
        return items.get(itemId);
    }

    @Override
    public List<Item> findByUserId(long userId) {
        var userItems = new ArrayList<Item>();
        for (Item item: items.values()) {
            if (item.getOwner().getId() == userId) {
                userItems.add(item);
            }
        }
        return userItems;
    }

    @Override
    public Item create(long userId, Item item) {
        genId++;
        if (item.getId() == 0) {
            item.setId(genId);
        }
        //item.setOwner(userId);
        items.put(item.getId(), item);
        log.info("Вещь c id " + item.getId() + " добавлена.");
        return item;
    }

    @Override
    public Item update(Item curItem, Item newItem) {
        if (newItem.getName() != null) {
            curItem.setName(newItem.getName());
        }
        if (newItem.getDescription() != null) {
            curItem.setDescription(newItem.getDescription());
        }
        if (newItem.getAvailable() != null) {
            curItem.setAvailable(newItem.getAvailable());
        }
        items.put(curItem.getId(), curItem);
        log.info("Вещь c id " + curItem.getId() + " обновлена.");
        return curItem;
    }

    @Override
    public List<Item> search(String text) {
        var foundItems = new ArrayList<Item>();
        for (Item item: items.values()) {
            if ((item.getName().toLowerCase().contains(text.toLowerCase())
                    || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                    && item.getAvailable()) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }


    @Override
    public void delete(long itemId) {
        items.remove(itemId);
        log.debug("Вещь с идентификатором {} удалёнв.", itemId);
    }
}
