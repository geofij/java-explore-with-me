package ru.yandex.practicum.comments.service;

import ru.yandex.practicum.comments.dto.CommentRequestDto;
import ru.yandex.practicum.comments.dto.CommentResponseDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public interface PrivateCommentService {
    CommentResponseDto createNewComment(long userId, long eventId, CommentRequestDto newComment);

    CommentResponseDto updateCommentById(long userId, long commentId, CommentRequestDto updateComment);

    void deleteCommentById(long userId, long commentId);

    List<CommentResponseDto> getUserComments(long userId, @PositiveOrZero int from, @Positive int size);
}
