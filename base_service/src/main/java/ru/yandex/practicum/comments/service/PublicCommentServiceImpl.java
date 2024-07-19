package ru.yandex.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.mapper.CommentMapper;
import ru.yandex.practicum.comments.storage.CommentRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.storage.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CommentResponseDto> getEventComments(long eventId, String sort, int from, int size) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id" + eventId + " not found"));

        Sort pageSort;

        if (sort.equals("NEW")) {
            pageSort = Sort.by(Sort.Direction.DESC, "created");
        } else {
            pageSort = Sort.by(Sort.Direction.ASC, "created");
        }

        Pageable page = PageRequest.of(from, size, pageSort);

        return CommentMapper.toCommentResponseList(commentRepository.findAllByEventId(eventId, page));
    }
}
