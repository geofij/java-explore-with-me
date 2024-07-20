package ru.yandex.practicum.event.mapper;

import ru.yandex.practicum.category.mapper.CategoryMapper;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.comments.mapper.CommentMapper;
import ru.yandex.practicum.comments.model.Comment;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.event.dto.Location;
import ru.yandex.practicum.user.mapper.UserMapper;
import ru.yandex.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {
    public static Event toEvent(NewEventDto eventDto, Category category, User initiator) {
        if (eventDto == null) {
            return new Event();
        }

        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .description(eventDto.getDescription())
                .lon(eventDto.getLocation().getLon())
                .lat(eventDto.getLocation().getLat())
                .paid(eventDto.isPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.isRequestModeration())
                .title(eventDto.getTitle())
                .category(category)
                .confirmedRequests(0)
                .createdOn(LocalDateTime.now())
                .state(EventState.PENDING)
                .views(0)
                .initiator(initiator)
                .eventDate(eventDto.getEventDate())
                .available(true)
                .build();
    }

    public static EventFullDto toFullDto(Event event) {
        if (event == null) {
            return new EventFullDto();
        }

        List<Comment> comments = new ArrayList<>();

        if (event.getComments() != null) {
            comments = event.getComments();
        }

        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(Location.builder()
                        .lat(event.getLat())
                        .lon(event.getLon())
                        .build())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .eventDate(event.getEventDate())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .comments(CommentMapper.toCommentInEventList(event.getComments()))
                .build();
    }

    public static EventShortDto toShortDto(Event event) {
        if (event == null) {
            return new EventShortDto();
        }

        int commentCount = 0;

        if (event.getComments() != null) {
            commentCount = event.getComments().size();
        }

        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .comments(commentCount)
                .build();
    }

    public static List<EventShortDto> toShortDtoList(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return new ArrayList<>();
        }

        return events.stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public static List<EventFullDto> toFullDtoList(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return new ArrayList<>();
        }

        return events.stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }
}
