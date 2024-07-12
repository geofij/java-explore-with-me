package ru.yandex.practicum.category.service;

import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    CategoryDto createNewCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(long catId);

    CategoryDto updateCategoryById(long catId, NewCategoryDto updateCategoryDto);
}
