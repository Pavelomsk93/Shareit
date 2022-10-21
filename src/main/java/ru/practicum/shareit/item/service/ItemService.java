package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems(Long userId);

    Item getItemById(Long itemId);

    List<Item> getItemSearch(String text);

    Item createItem(Item item, Long userId);

    void removeItem(Long id);

    Item patchItem(Item item, Long userId, Long id);
}
