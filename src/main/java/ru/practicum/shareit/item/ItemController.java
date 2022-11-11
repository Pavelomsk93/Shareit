package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.service.CommentService;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final CommentService commentService;

    @GetMapping
    public List<ItemDtoWithBooking> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getAllItems(userId);
    }

    @GetMapping(value = "/{itemId}")
    public ItemDtoWithBooking getItemById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId) {
        return itemService.getItemById(userId, itemId);
    }

    @GetMapping(value = "/search")
    public List<ItemDto> getItemSearch(@RequestParam(name = "text") String text) {
        return itemService.getItemSearch(text);
    }

    @PostMapping
    public ItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto, userId);
    }

    @PostMapping(value = "/{itemId}/comment")
    public CommentDto createComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId,
            @RequestBody CommentDto commentDto) {
        return commentService.createComment(commentDto, itemId, userId);
    }

    @DeleteMapping(value = "/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        itemService.removeItemById(itemId);
    }

    @PatchMapping(value = "/{itemId}")
    public ItemDto patchItem(
            @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId) {
        return itemService.patchItem(itemDto, userId, itemId);
    }
}