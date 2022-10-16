package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private  final UserRepository userRepository;


    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserDto userDto = UserMapper.toUserDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.toUserDto(userRepository.findById(id));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        if (user.getEmail() == null) {
            throw new ValidationException("E-mail не должен быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Введен некорректный e-mail.");
        }
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = UserMapper.toUser(userDto);
        if (userDto.getEmail() != null && userDto.getName() == null) {
            return UserMapper.toUserDto(userRepository.updateUserEmail(user, id));
        } else if (userDto.getName() != null && userDto.getEmail() == null) {
            return UserMapper.toUserDto(userRepository.updateUserName(user, id));
        } else {
            return UserMapper.toUserDto(userRepository.updateUser(user, id));
        }
    }
}
