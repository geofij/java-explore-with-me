package ru.yandex.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.category.dto.CategoryDto;
import ru.yandex.practicum.category.dto.NewCategoryDto;
import ru.yandex.practicum.category.mapper.CategoryMapper;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.ConflictException;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CategoryDto createNewCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(newCategoryDto)));
    }

    @Override
    @Transactional
    public void deleteCategoryById(long catId) {
        Category category = findCategoryById(catId);
        List<Event> events = eventRepository.findAllByCategory(category);

        if (events.isEmpty()) {
            categoryRepository.deleteById(catId);
        } else {
            throw new ConflictException("Category contain Events");
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategoryById(long catId, NewCategoryDto updateCategoryDto) {
        Category category = findCategoryById(catId);
        category.setName(updateCategoryDto.getName());

        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    private Category findCategoryById(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException("Category with id " + catId + " not found"));
    }
}
