package ru.yandex.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.comments.dto.CommentRequestDto;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.mapper.CommentMapper;
import ru.yandex.practicum.comments.model.Comment;
import ru.yandex.practicum.comments.storage.CommentRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDto createNewComment(long userId, long eventId, CommentRequestDto newComment) {
        User author = checkUser(userId);
        Event event = checkEvent(eventId);

        return CommentMapper.toCommentResponse(commentRepository.save(CommentMapper.toNewComment(newComment, event, author)));
    }

    @Override
    public CommentResponseDto updateCommentById(long userId, long commentId, CommentRequestDto updateComment) {
        checkUser(userId);
        Comment comment = checkComment(commentId);

        if (comment.getAuthor().getId() != userId) {
            throw new NotFoundException("Comment with id-" + commentId + " where user id-" + userId + " not found");
        }

        comment.setText(updateComment.getText());
        comment.setUpdated(LocalDateTime.now());

        return CommentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(long userId, long commentId) {
        checkUser(userId);
        Comment comment = checkComment(commentId);

        if (comment.getAuthor().getId() != userId) {
            throw new NotFoundException("Comment with id-" + commentId + " where user id-" + userId + " not found");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponseDto> getUserComments(long userId, int from, int size) {
        checkUser(userId);

        Sort sortByCreated = Sort.by(Sort.Direction.ASC, "created");
        Pageable page = PageRequest.of(from, size, sortByCreated);

        return CommentMapper.toCommentResponseList(commentRepository.findAllByAuthorId(userId, page));
    }

    private Comment checkComment(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));
    }

    private User checkUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    private Event checkEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Category with id " + eventId + " not found"));
    }
}
