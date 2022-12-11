package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.dto.ItemRequestDto;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @GetMapping
    public ResponseEntity<Object> getAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.getAllItemRequest(userId);
    }

    @GetMapping(value = "/{requestId}")
    private ResponseEntity<Object> getItemRequestById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long requestId) {
        return itemRequestClient.getItemRequestById(userId, requestId);
    }

    @GetMapping(value = "/all")
    private ResponseEntity<Object> getItemRequestOtherUsers(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        if (from < 0 || size <= 0) {
            throw new ValidationException("Переданы некорректные значения from и/или size");
        }
        return itemRequestClient.getItemRequestOtherUsers(userId, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemRequestDto itemRequestDto) {
        if (itemRequestDto.getDescription() == null) {
            throw new ValidationException("Описание для создаваемого запроса не может быть пустым.");
        }
        return itemRequestClient.createItemRequest(userId, itemRequestDto);
    }
}