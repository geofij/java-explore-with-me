package ru.yandex.practicum.category.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.category.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c")
    List<Category> findAllCategories(Pageable page);

    List<Category> findAllIdIn(List<Long> ids);
}
