package ru.yandex.practicum.request.mapper;

import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {
    public Request toRequest(ParticipationRequestDto requestDto, Event event, User user) {
        return Request.builder()
                .event(event)
                .created(requestDto.getCreated())
                .requester(user)
                .status(requestDto.getStatus())
                .build();
    }

    public EventRequestStatusUpdateResult toRequestStatusUpdateResult(List<Request> rejected, List<Request> confirmed) {
        return EventRequestStatusUpdateResult.builder()
                .rejectedRequests(toRequestDtoList(rejected))
                .confirmedRequests(toRequestDtoList(confirmed))
                .build();
    }

    private static ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    private List<ParticipationRequestDto> toRequestDtoList(List<Request> requests) {
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }
}
