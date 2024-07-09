package ru.yandex.practicum.event.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users")
public class PrivateEventController {
    @PostMapping("/{userId}/events")
    public void createNewEvent(@PathVariable long userId) {

    }

    @PatchMapping("/{userId}/events/{eventId}")
    public void updateEventById(@PathVariable long userId,
                                @PathVariable long eventId) {

    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public void updateEventRequests(@PathVariable long userId,
                                    @PathVariable long eventId) {

    }

    @GetMapping("/{userId}/events")
    public void getUserEvents(@PathVariable long userId) {

    }

    @GetMapping("/{userId}/events/{eventId}")
    public void getUserEventById(@PathVariable long userId,
                                 @PathVariable long eventId) {

    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public void getUserEventRequests(@PathVariable long userId,
                                     @PathVariable long eventId) {

    }
}
