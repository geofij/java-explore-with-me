package ru.yandex.practicum.category.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin/categories")
public class AdminCategoryController {
    @PostMapping
    public void createNewCategory() {

    }

    @DeleteMapping("/{catId}")
    public void deleteCategoryById(@PathVariable long catId) {

    }

    @PatchMapping("/{catId}")
    public void updateCategoryById(@PathVariable long catId) {

    }
}
