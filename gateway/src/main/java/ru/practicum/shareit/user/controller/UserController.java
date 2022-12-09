package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.client.UserClient;
import ru.practicum.shareit.user.dto.UserDto;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return userClient.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        return userClient.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        if (userDto.getEmail() == null) {
            throw new ValidationException("E-mail не должен быть пустым.");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("Введен некорректный e-mail.");
        }
        return userClient.createUser(userDto);
    }

    @DeleteMapping(value = "/{id}")
    public void removeUser(@PathVariable Long id) {
        userClient.removeUser(id);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> patchUser(
            @RequestBody UserDto userDto,
            @PathVariable Long id) {
        return userClient.patchUser(userDto, id);
    }
}