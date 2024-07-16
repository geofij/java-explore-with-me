package ru.yandex.practicum.comments.mapper;

import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.dto.CommentRequestDto;
import ru.yandex.practicum.comments.model.Comment;
import ru.yandex.practicum.event.mapper.EventMapper;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.user.mapper.UserMapper;
import ru.yandex.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static Comment toNewComment(CommentRequestDto commentRequest, Event event, User author) {
        if (commentRequest == null) {
            return new Comment();
        }

        return Comment.builder()
                .text(commentRequest.getText())
                .event(event)
                .author(author)
                .created(LocalDateTime.now())
                .build();
    }

    public static CommentResponseDto toCommentResponse(Comment comment) {
        if (comment == null) {
            return new CommentResponseDto();
        }

        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .event(EventMapper.toShortDto(comment.getEvent()))
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .build();
    }

    public static List<CommentResponseDto> commentResponseList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }
}
