package ru.yandex.practicum.category.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
@RestController("/categories")
public class PublicCategoryController {
    @GetMapping
    public void getAllCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                 @RequestParam(defaultValue = "10") @Positive int size) {

    }

    @GetMapping("/{catId}")
    public void getCategoryById(@PathVariable long catId) {

    }
}
