package ru.yandex.practicum.request.mapper;

import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {
    public static EventRequestStatusUpdateResult toRequestStatusUpdateResult(List<Request> rejected, List<Request> confirmed) {
        return EventRequestStatusUpdateResult.builder()
                .rejectedRequests(toRequestDtoList(rejected))
                .confirmedRequests(toRequestDtoList(confirmed))
                .build();
    }

    public static ParticipationRequestDto toRequestDto(Request request) {
        if (request == null) {
            return new ParticipationRequestDto();
        }

        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .created(request.getCreated())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> toRequestDtoList(List<Request> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }
}
