package ru.yandex.practicum.comments.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.yandex.practicum.comments.model.Comment;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.user.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Collection;

public class CommentSpecification {
    public static Specification<Comment> byAuthorIn(Collection<User> authors) {
        return (Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                root.get("author").in(authors);
    }

    public static Specification<Comment> byEventIn(Collection<Event> events) {
        return (Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                root.get("event").in(events);
    }

    public static Specification<Comment> byCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.between(root.get("created"), startDate, endDate);
    }
}
