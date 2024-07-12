package ru.yandex.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.storage.RequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository repository;

    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId, ParticipationRequestDto newRequestDto) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) {
        return null;
    }
}
