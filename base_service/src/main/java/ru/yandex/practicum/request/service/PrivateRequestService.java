package ru.yandex.practicum.request.service;

import ru.yandex.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateRequestService {
    ParticipationRequestDto createRequest(long userId, long eventId, ParticipationRequestDto newRequestDto);

    ParticipationRequestDto cancelRequest(long userId, long requestId);

    List<ParticipationRequestDto> getUserRequests(long userId);
}
