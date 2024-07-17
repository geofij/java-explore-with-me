package ru.yandex.practicum.comments.service;

import ru.yandex.practicum.comments.dto.CommentResponseDto;

import java.util.List;

public interface PublicCommentService {
    List<CommentResponseDto> getEventComments(String sort, int from, int size);
}
