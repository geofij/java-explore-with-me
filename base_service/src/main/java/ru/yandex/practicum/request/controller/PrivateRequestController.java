package ru.yandex.practicum.request.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class PrivateRequestController {
    @PostMapping("/{userId}/requests")
    public void createRequest(@PathVariable long userId,
                              @RequestParam(name = "eventId") long eventId) {

    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public void cancelRequest(@PathVariable long userId,
                              @PathVariable long requestId) {

    }

    @GetMapping("/{userId}/requests")
    public void getUserRequests(@PathVariable long userId) {

    }
}
