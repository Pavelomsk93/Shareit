package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public List<Item> getAllItems(Long userId) {
        if(userRepository.findById(userId)==null){
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
        if(userRepository.findById(userId)==null){
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
        if (item.getName().isEmpty() || item.getDescription() == null || item.getAvailable() == null) {
            throw new ValidationException("Данно поле не может быть пустым.");
        }
        item.setOwner(userRepository.findById(userId));
        return itemRepository.createItem(item);
    }

    @Override
    public void removeItem(Long id) {
        itemRepository.removeItem(id);
    }

    @Override
    public Item patchItem(Item item, Long userId, Long id) {
        if(userRepository.findById(userId)==null){
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
        if(!(itemRepository.getItemById(id).getOwner().getId()==userId)){
            throw new EntityNotFoundException("Пользователь не владеет вещью");
        }
        if(item.getName()!=null&&item.getDescription()==null&&item.getAvailable()==null){
            return itemRepository.patchItemName(item,id);
        } else if(item.getDescription()!=null&&item.getName()==null&&item.getAvailable()==null) {
            return itemRepository.patchItemDescription(item,id);
        } else if(item.getAvailable()!=null&&item.getName()==null&&item.getDescription()==null){
            return itemRepository.patchItemAvailable(item,id);
        }else{
            return itemRepository.patchItem(item,id);
        }
    }
}
