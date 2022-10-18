package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
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
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User updateUser(User user, Long id) {
        if (user.getEmail() != null && user.getName() == null) {
            return userRepository.updateUserEmail(user, id);
        } else if (user.getName() != null && user.getEmail() == null) {
            return userRepository.updateUserName(user, id);
        } else {
            return userRepository.updateUser(user, id);
        }
    }
}
