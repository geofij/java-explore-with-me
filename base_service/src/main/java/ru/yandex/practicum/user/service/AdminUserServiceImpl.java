package ru.yandex.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        return null;
    }

    @Override
    public UserDto createNewUser(NewUserRequest newUserDto) {
        return null;
    }

    @Override
    public void deleteUserById(long userId) {

    }
}
