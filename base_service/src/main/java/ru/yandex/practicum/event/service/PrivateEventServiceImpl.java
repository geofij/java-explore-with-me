package ru.yandex.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository repository;

    @Override
    public EventFullDto createNewEvent(long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto updateEventById(long userId, long eventId, UpdateEventUserRequest updateEventDto) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequests(long userId, long eventId, EventRequestStatusUpdateRequest updateRequestStatusDto) {
        return null;
    }

    @Override
    public List<EventShortDto> getUserEvents(long userId) {
        return null;
    }

    @Override
    public EventFullDto getUserEventById(long userId, long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(long userId, long eventId) {
        return null;
    }
}
