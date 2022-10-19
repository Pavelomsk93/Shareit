package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return UserMapper.toUserDto(userService.getUserById(id));
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userService.createUser(user));
    }

    @DeleteMapping(value = "/{id}")
    public void removeUser(@PathVariable Long id) {
        userService.removeUser(id);
    }

    @PatchMapping(value = "/{id}")
    public UserDto patchUser(
            @RequestBody UserDto userDto,
            @PathVariable Long id) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(userService.updateUser(user, id));
    }
}
