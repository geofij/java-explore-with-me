package ru.yandex.practicum.user.service;

import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto createNewUser(NewUserRequest newUserDto);

    void deleteUserById(long userId);
}