package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(Long id);

    User save(User userDto);

    User updateUser(User user,Long id);

    User updateUserEmail(User user,Long id);

    User updateUserName(User user,Long id);

    void delete(Long userId);
}
