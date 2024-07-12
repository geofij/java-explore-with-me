package ru.yandex.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.storage.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository repository;

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        return null;
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        return null;
    }
}
