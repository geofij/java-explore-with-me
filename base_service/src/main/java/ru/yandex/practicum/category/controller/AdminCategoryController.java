package ru.yandex.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;
import ru.yandex.practicum.category.service.AdminCategoryService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createNewCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        return service.createNewCategory(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable long catId) {
        service.deleteCategoryById(catId);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategoryById(@PathVariable long catId,
                                          @Valid @RequestBody NewCategoryDto updateCategoryDto) {
        return service.updateCategoryById(catId, updateCategoryDto);
    }
}
