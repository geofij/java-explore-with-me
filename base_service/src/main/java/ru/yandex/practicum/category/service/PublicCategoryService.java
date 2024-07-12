package ru.yandex.practicum.category.service;

import ru.yandex.practicum.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(long catId);
}
