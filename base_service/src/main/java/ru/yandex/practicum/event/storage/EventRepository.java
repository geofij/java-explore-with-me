package ru.yandex.practicum.event.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCategory(Category category);

    List<Event> findAllByIdIn(List<Long> ids);
}
