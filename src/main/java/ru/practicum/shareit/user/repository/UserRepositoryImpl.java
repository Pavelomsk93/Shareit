package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EntityAlreadyExistException;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Map<Long, User> users = new HashMap<>();
    private final Set<String> usersEmail = new HashSet<>();
    private long id = 0;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findById(Long id) {
        if (users.containsKey(id)){
            return users.get(id);
        }else{
            throw new EntityNotFoundException("Такого ользователя не существует");
        }

    }

    @Override
    public User save(User user) {
        if(usersEmail.contains(user.getEmail())){
            throw  new EntityAlreadyExistException("Пользователь с таким email уже существует");
        }
        user.setId(++id);
        users.put(user.getId(),user);
        usersEmail.add(user.getEmail());
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User user, Long id) {
        if (usersEmail.contains(user.getEmail())) {
            throw new EntityNotFoundException(String.format("Пользователя с %s не существует.", user.getEmail()));
        }
        User userUpdate = users.get(id);
        usersEmail.remove(userUpdate.getEmail());

        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        users.put(userUpdate.getId(), userUpdate);
        usersEmail.add(userUpdate.getEmail());
        return userUpdate;
    }

    @Override
    public User updateUserName(User user, Long id) {
        User userUpdate = users.get(id);
        userUpdate.setName(user.getName());
        users.put(userUpdate.getId(), userUpdate);
        return userUpdate;
    }

    @Override
    public User updateUserEmail(User user, Long id) {
        if (usersEmail.contains(user.getEmail())) {
            throw new EntityAlreadyExistException(String.format("Пользователя с %s уже существует.", user.getEmail()));
        }
        User userUpdate = users.get(id);
        usersEmail.remove(userUpdate.getEmail());

        userUpdate.setEmail(user.getEmail());
        users.put(userUpdate.getId(), userUpdate);
        usersEmail.add(userUpdate.getEmail());
        return userUpdate;
    }

    @Override
    public void delete(Long userId) {
        if(users.containsKey(userId)){
            usersEmail.remove(users.get(userId).getEmail());
            users.remove(userId);
        }else{
            throw new EntityNotFoundException("Такого пользователя не существует");
        }
    }
}
