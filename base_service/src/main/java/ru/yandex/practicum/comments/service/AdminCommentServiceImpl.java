package ru.yandex.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.dto.DeleteCommentAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService{
    @Override
    public List<CommentResponseDto> getComments(int from, int size, List<Long> eventIds, List<Long> userIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean isUpdated, String sort) {
        return null;
    }

    @Override
    public void deleteComments(DeleteCommentAdminRequest deleteCommentAdminRequest) {

    }
}
