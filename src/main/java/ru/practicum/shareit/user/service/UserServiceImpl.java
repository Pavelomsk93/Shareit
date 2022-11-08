package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(
                        String.format("Пользователь %s не существует.", id)));
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (user.getEmail() == null) {
            log.error("Email у User с именем {} не может быть пустым", user.getName());
            throw new ValidationException("E-mail не должен быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            log.error("Email у User с именем {} должен быть валидным", user.getName());
            throw new ValidationException("Введен некорректный e-mail.");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь %s не существует.", id)));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User updateUser(User user, Long id) {
        final User userUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Пользователь с %s не существует.", id)));
        if (user.getEmail() != null && user.getName() == null) {
            userUpdate.setEmail(user.getEmail());
            userRepository.save(userUpdate);
            return userUpdate;
        } else if (user.getName() != null && user.getEmail() == null) {
            userUpdate.setName(user.getName());
            userRepository.save(userUpdate);
            return userUpdate;
        } else {
            userUpdate.setName(user.getName());
            userUpdate.setEmail(user.getEmail());
            userRepository.save(userUpdate);
            return userUpdate;
        }
    }
}
