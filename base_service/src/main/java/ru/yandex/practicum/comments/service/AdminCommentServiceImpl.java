package ru.yandex.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.dto.DeleteCommentAdminRequest;
import ru.yandex.practicum.comments.mapper.CommentMapper;
import ru.yandex.practicum.comments.model.Comment;
import ru.yandex.practicum.comments.specification.CommentSpecification;
import ru.yandex.practicum.comments.storage.CommentRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(int from, int size, List<Long> eventIds, List<Long> userIds, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean isUpdated, String sort) {
        Sort pageSort;

        if (sort.equals("NEW")) {
            pageSort = Sort.by(Sort.Direction.DESC, "created");
        } else {
            pageSort = Sort.by(Sort.Direction.ASC, "created");
        }

        Pageable page = PageRequest.of(from, size, pageSort);
        Specification<Comment> spec = Specification.where(null);

        if (eventIds != null && !eventIds.isEmpty()) {
            List<Event> events = eventIds.stream().map(eventId -> eventRepository.findById(eventId)
                            .orElseThrow(() -> new NotFoundException("Event with id" + eventId + " not found")))
                    .collect(Collectors.toList());
            spec = spec.and(CommentSpecification.byEventIn(events));
        }

        if (userIds != null && !userIds.isEmpty()) {
            List<User> users = userIds.stream().map(user -> userRepository.findById(user).orElseThrow(() ->
                    new NotFoundException("User not found"))).collect(Collectors.toList());
            spec = spec.and(CommentSpecification.byAuthorIn(users));
        }

        if (rangeStart != null && rangeEnd != null) {
            spec = spec.and(CommentSpecification.byCreatedBetween(rangeStart, rangeEnd));
        }

        return CommentMapper.toCommentResponseList(commentRepository.findAll(spec, page).getContent());
    }

    @Override
    @Transactional
    public void deleteComments(DeleteCommentAdminRequest deleteCommentAdminRequest) {
        commentRepository.deleteByIdIn(deleteCommentAdminRequest.getCommentIds());
    }
}
