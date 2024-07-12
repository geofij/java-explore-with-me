package ru.yandex.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;
import ru.yandex.practicum.category.storage.CategoryRepository;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository repository;

    @Override
    public CategoryDto createNewCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategoryById(long catId) {

    }

    @Override
    public CategoryDto updateCategoryById(long catId, NewCategoryDto updateCategoryDto) {
        return null;
    }
}
