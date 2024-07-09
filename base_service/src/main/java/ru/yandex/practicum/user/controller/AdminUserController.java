package ru.yandex.practicum.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController("/admin/users")
public class AdminUserController {
    @GetMapping
    public void getUsers(@RequestParam(required = false) List<Long> ids,
                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                         @RequestParam(name = "size", defaultValue = "10") @Positive int size) {

    }

    @PostMapping
    public void createNewUser() {

    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable long userId) {

    }
}
