package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EntityAlreadyExistException;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private static final Map<Long, User> users = new HashMap<>();
    private final Set<String> usersEmail = new HashSet<>();
    private long id = 0;

    @Override
    public List<User> findAll() {
        log.info("Все пользователи:");
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(Long userId) {
        if (users.containsKey(userId)) {
            log.info("Пользователь с id{}",userId);
            return users.get(userId);
        } else {
            log.error("User с id {} не существует", userId);
            throw new EntityNotFoundException("Такого пользователя не существует");
        }

    }

    @Override
    public User save(User user) {
        if (usersEmail.contains(user.getEmail())) {
            log.error("User с email {} уже существует", user.getEmail());
            throw new EntityAlreadyExistException("Пользователь с таким email уже существует");
        }
        user.setId(++id);
        users.put(user.getId(), user);
        usersEmail.add(user.getEmail());
        log.info("User с id {} создан", user.getId());
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User user, Long userId) {
        if (usersEmail.contains(user.getEmail())) {
            log.error("User с email {} не существует", user.getEmail());
            throw new EntityNotFoundException(String.format("Пользователя с %s не существует.", user.getEmail()));
        }
        User userUpdate = users.get(userId);
        usersEmail.remove(userUpdate.getEmail());

        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        users.put(userUpdate.getId(), userUpdate);
        usersEmail.add(userUpdate.getEmail());
        log.info("User с id {} обновлён", userId);
        return userUpdate;
    }

    @Override
    public User updateUserName(User user, Long userId) {
        User userUpdate = users.get(userId);
        userUpdate.setName(user.getName());
        users.put(userUpdate.getId(), userUpdate);
        log.info("User с id {} обновлён", userId);
        return userUpdate;
    }

    @Override
    public User updateUserEmail(User user, Long id) {
        if (usersEmail.contains(user.getEmail())) {
            log.error("User с email {} уже существует", user.getEmail());
            throw new EntityAlreadyExistException(String.format("Пользователя с %s уже существует.", user.getEmail()));
        }
        User userUpdate = users.get(id);
        usersEmail.remove(userUpdate.getEmail());

        userUpdate.setEmail(user.getEmail());
        users.put(userUpdate.getId(), userUpdate);
        usersEmail.add(userUpdate.getEmail());
        log.info("User с id {} обновлён", id);
        return userUpdate;
    }

    @Override
    public void delete(Long userId) {
        if (users.containsKey(userId)) {
            usersEmail.remove(users.get(userId).getEmail());
            log.info("User с id {} удалён", userId);
            users.remove(userId);
        } else {
            log.error("User с id {} не существует", userId);
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
    }
}
