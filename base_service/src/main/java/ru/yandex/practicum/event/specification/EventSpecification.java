package ru.yandex.practicum.event.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.user.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collection;

public class EventSpecification {
    public static Specification<Event> byInitiatorIn(Collection<User> initiators) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                root.get("initiator").in(initiators);
    }

    public static Specification<Event> byStateIn(Collection<EventState> states) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                root.get("state").in(states);
    }

    public static Specification<Event> byCategoryIn(Collection<Category> category) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                root.get("category").in(category);
    }

    public static Specification<Event> byEventDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.between(root.get("eventDate"), startDate, endDate);
    }

    public static Specification<Event> byAnnotation(String searchText) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), "%" + searchText.toLowerCase() + "%");
    }

    public static Specification<Event> byPaid(boolean paid) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paid"), paid);
    }

    public static Specification<Event> byAvailable(boolean onlyAvailable) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.equal(root.get("available"), onlyAvailable);
    }
}
