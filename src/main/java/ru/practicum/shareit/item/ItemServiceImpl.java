package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final UserService userService;

    @Override
    public Item addNewItem(long userId, Item item) {
        var user = userService.get(userId);
        var newItem = itemDao.create(userId, item);
        log.info("Создана новая вещь пользователем с Id " + userId);
        return newItem;
    }

    @Override
    public Item get(long userId, long itemId) {
        Item item = itemDao.get(itemId);
        if (item == null) {
            log.error("Не существует вещи с id " + itemId);
            throw new NullPointerException("Не существует вещи с id " + itemId);
        }
        return item;
    }

    @Override
    public Item update(long itemId, Item item, long userId) {
        var curUser = userService.get(userId);
        var curItem = get(userId, itemId);
        if (userId != curItem.getOwner()) {
            throw new NotFoundException("Пользователь не является владельцем вещи.");
        }

        var updatedItem = itemDao.update(curItem, item);
        log.info("Вещь " + itemId + " обновлена.");
        return updatedItem;
    }

    @Override
    public List<Item> getItems(long userId) {
        var curUser = userService.get(userId);
        return itemDao.findByUserId(userId);
    }

    @Override
    public List<Item> search(long userId, String text) {
        return text.isEmpty() ? new ArrayList<Item>() : itemDao.search(text);
    }

    @Override
    public void deleteItem(long userId, long itemId) {
        var curUser = userService.get(userId);
        var curItem = get(userId, itemId);
        if (userId != curItem.getOwner()) {
            throw new NotFoundException("Пользователь не является владельцем вещи.");
        }
        itemDao.delete(itemId);
        log.info("Вещь " + itemId + " удалена.");
    }
}
