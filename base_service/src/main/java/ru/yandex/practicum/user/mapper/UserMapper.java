package ru.yandex.practicum.user.mapper;

import ru.yandex.practicum.user.dto.NewUserRequest;
import ru.yandex.practicum.user.dto.UserDto;
import ru.yandex.practicum.user.dto.UserShortDto;
import ru.yandex.practicum.user.model.User;

public class UserMapper {
    public static User toUser(NewUserRequest newUser) {
        if (newUser == null) {
            return new User();
        }

        return User.builder()
                .email(newUser.getEmail())
                .name(newUser.getName())
                .build();
    }

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return new UserDto();
        }

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserShortDto toUserShortDto(User user) {
        if (user == null) {
            return new UserShortDto();
        }

        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
