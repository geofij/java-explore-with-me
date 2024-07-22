package ru.yandex.practicum.comments.service;

import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.dto.DeleteCommentAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminCommentService {
    List<CommentResponseDto> getComments(int from,
                                         int size,
                                         List<Long> eventIds,
                                         List<Long> userIds,
                                         LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd,
                                         Boolean isUpdated,
                                         String sort);

    void deleteComments(DeleteCommentAdminRequest deleteCommentAdminRequest);
}
