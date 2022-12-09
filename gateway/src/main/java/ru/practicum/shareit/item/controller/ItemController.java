package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.client.CommentClient;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;
    private final CommentClient commentClient;

    @GetMapping
    public ResponseEntity<Object> getAllItems(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        if (from < 0 || size <= 0) {
            throw new ValidationException("Переданы некорректные значения from и/или size");
        }
        return itemClient.getAllItems(userId, from, size);
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<Object> getItemById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId) {
        return itemClient.getItemById(userId, itemId);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> getItemSearch(
            @RequestParam(name = "text") String text,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        if (from < 0 || size <= 0) {
            throw new ValidationException("Переданы некорректные значения from и/или size");
        }
        return itemClient.getItemSearch(text, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemDto itemDto) {

        if (itemDto.getName().isBlank() || itemDto.getDescription() == null || itemDto.getAvailable() == null) {
            throw new ValidationException("Данное поле не может быть пустым.");
        }
        return itemClient.createItem(itemDto, userId);
    }

    @PostMapping(value = "/{itemId}/comment")
    public ResponseEntity<Object> createComment(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId,
            @RequestBody CommentDto commentDto) {
        if (commentDto.getText().isBlank()) {
            throw new ValidationException("Комментарий не может быть пустым.");
        }
        return commentClient.createComment(userId, itemId, commentDto);
    }

    @DeleteMapping(value = "/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        itemClient.removeItemById(itemId);
    }

    @PatchMapping(value = "/{itemId}")
    public ResponseEntity<Object> patchItem(
            @RequestBody ItemDto itemDto,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long itemId) {
        return itemClient.patchItem(itemDto, userId, itemId);
    }
}