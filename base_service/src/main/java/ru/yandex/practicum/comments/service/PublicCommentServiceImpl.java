package ru.yandex.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.comments.dto.CommentResponseDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    @Override
    public List<CommentResponseDto> getEventComments(String sort, int from, int size) {
        return null;
    }
}
