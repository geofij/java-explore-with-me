package ru.yandex.practicum.category.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIdIn(List<Long> ids);
}
