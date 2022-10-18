package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public List<Item> getAllItems(Long userId) {
        if (userRepository.findById(userId) == null) {
            log.error("User с id {} не существует", userId);
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
        return itemRepository.getAllItem(userId);
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.getItemById(itemId);
    }

    @Override
    public List<Item> getItemSearch(String text) {
        return itemRepository.getItemSearch(text);
    }

    @Override
    public Item createItem(Item item, Long userId) {
        if (userRepository.findById(userId) == null) {
            log.error("User с id {} не существует", userId);
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
        if (item.getName().isEmpty() || item.getDescription() == null || item.getAvailable() == null) {
            log.error("Данное поле не может быть пустым.");
            throw new ValidationException("Данное поле не может быть пустым.");
        }
        item.setOwner(userRepository.findById(userId));
        return itemRepository.createItem(item);
    }

    @Override
    public void removeItem(Long id) {
        itemRepository.removeItem(id);
    }

    @Override
    public Item patchItem(Item item, Long userId, Long itemId) {
        if (userRepository.findById(userId) == null) {
            log.error("User с id {} не существует", userId);
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
        if (!(itemRepository.getItemById(itemId).getOwner().getId() == userId)) {
            log.error("User с id {} не владеет вещью", userId);
            throw new EntityNotFoundException("Пользователь не владеет вещью");
        }
        if (item.getName() != null && item.getDescription() == null && item.getAvailable() == null) {
            return itemRepository.patchItemName(item, itemId);
        } else if (item.getDescription() != null && item.getName() == null && item.getAvailable() == null) {
            return itemRepository.patchItemDescription(item, itemId);
        } else if (item.getAvailable() != null && item.getName() == null && item.getDescription() == null) {
            return itemRepository.patchItemAvailable(item, itemId);
        } else {
            return itemRepository.patchItem(item, itemId);
        }
    }
}
