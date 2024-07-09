package ru.yandex.practicum.event.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController("/events")
public class PublicEventController {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    @GetMapping
    public void getEvents(@RequestParam(required = false) String text,
                          @RequestParam(required = false) List<Long> categories,
                          @RequestParam(required = false) Boolean paid,
                          @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT) LocalDateTime rangeStart,
                          @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT) LocalDateTime rangeEnd,
                          @RequestParam(required = false) Boolean onlyAvailable,
                          @RequestParam(defaultValue = "") String sort,
                          @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                          @RequestParam(name = "size", defaultValue = "10") @Positive int size) {

    }

    @GetMapping("/{eventId}")
    public void getEventById(@PathVariable long eventId) {

    }
}
