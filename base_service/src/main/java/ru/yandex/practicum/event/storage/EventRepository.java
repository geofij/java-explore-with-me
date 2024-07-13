package ru.yandex.practicum.event.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByCategory(Category category);

    List<Event> findAllByIdIn(List<Long> ids);

    List<Event> findAllByInitiator_Id(long initiator_id, Pageable page);

    Page<Event> findAll(Specification<Event> spec, Pageable pageable);
}
