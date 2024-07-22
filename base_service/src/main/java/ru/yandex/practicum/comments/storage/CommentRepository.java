package ru.yandex.practicum.comments.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.comments.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthorId(long authorId, Pageable page);

    void deleteByIdIn(List<Long> commentIds);

    List<Comment> findAllByEventId(long eventId, Pageable page);

    Page<Comment> findAll(Specification<Comment> spec, Pageable page);
}
