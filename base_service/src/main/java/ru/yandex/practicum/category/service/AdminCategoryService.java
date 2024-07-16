package ru.yandex.practicum.category.service;

import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    CategoryDto createNewCategory(NewCategoryDto newCategoryDto);

    void deleteCategoryById(long categoryId);

    CategoryDto updateCategoryById(long categoryId, NewCategoryDto updateCategoryDto);
}
