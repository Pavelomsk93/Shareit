package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItems(userId).stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId) {
        return ItemMapper.toItemDto(itemService.getItemById(itemId));
    }

    @GetMapping(value = "/search")
    public List<ItemDto> getItemSearch(@RequestParam(name = "text") String text) {
        return itemService.getItemSearch(text).stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @PostMapping
    public ItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.createItem(item, userId));
    }

    @DeleteMapping(value = "/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        itemService.removeItem(itemId);
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto patchItem(
            @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId) {
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(itemService.patchItem(item, userId, itemId));
    }
}
