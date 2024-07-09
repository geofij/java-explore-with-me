package ru.yandex.practicum.compilation.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
@RestController("/compilations")
public class PublicCompilationController {
    @GetMapping
    public void getCompilations(@RequestParam(required = false) Boolean pinned,
                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                @RequestParam(defaultValue = "10") @Positive int size) {

    }

    @GetMapping("{compId}")
    public void getCompilationById(@PathVariable long compId) {

    }
}
