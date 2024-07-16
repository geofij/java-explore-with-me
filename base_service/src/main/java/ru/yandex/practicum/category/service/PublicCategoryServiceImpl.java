package ru.yandex.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.mapper.CategoryMapper;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories(int from, int size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);

        return CategoryMapper.toCategoryDtoList(repository.findAll(page).getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(long categoryId) {
        Category category = repository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category with id " + categoryId + " not found"));

        return CategoryMapper.toCategoryDto(category);
    }
}
