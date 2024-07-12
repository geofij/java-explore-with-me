package ru.yandex.practicum.event.service;

import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {
    EventFullDto createNewEvent(long userId, NewEventDto newEventDto);

    EventFullDto updateEventById(long userId, long eventId, UpdateEventUserRequest updateEventDto);

    EventRequestStatusUpdateResult updateEventRequests(long userId, long eventId, EventRequestStatusUpdateRequest updateRequestStatusDto);

    List<EventShortDto> getUserEvents(long userId);

    EventFullDto getUserEventById(long userId, long eventId);

    List<ParticipationRequestDto> getUserEventRequests(long userId, long eventId);
}
