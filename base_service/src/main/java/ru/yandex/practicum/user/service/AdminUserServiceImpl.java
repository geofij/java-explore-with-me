package ru.yandex.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.user.mapper.UserMapper;
import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        List<User> users;

        if (ids != null && !ids.isEmpty()) {
            users = repository.findAllByIdIn(ids, page);
        } else {
            users = repository.findAll(page).getContent();
        }

        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto createNewUser(NewUserRequest newUserDto) {
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(newUserDto)));
    }

    @Transactional
    @Override
    public void deleteUserById(long userId) {
        User user = repository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));

        repository.delete(user);
    }
}
