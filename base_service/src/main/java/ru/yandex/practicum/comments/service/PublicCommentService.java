package ru.yandex.practicum.comments.service;

import ru.yandex.practicum.comments.dto.CommentResponseDto;

import java.util.List;

public interface PublicCommentService {
    List<CommentResponseDto> getEventComments(long eventId, String sort, int from, int size);
}
