package ru.yandex.practicum.event.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController("/admin/events")
public class AdminEventController {
    @GetMapping
    public void getEvents(@RequestParam(required = false) List<Long> users,
                          @RequestParam(required = false) List<String> states,
                          @RequestParam(required = false) List<Long> categories,
                          @RequestParam(required = false) String rangeStart,
                          @RequestParam(required = false) String rangeEnd,
                          @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                          @RequestParam(name = "size", defaultValue = "10") @Positive int size) {

    }

    @PatchMapping("/{eventId}")
    public void updateEventById(@PathVariable long eventId) {

    }
}
