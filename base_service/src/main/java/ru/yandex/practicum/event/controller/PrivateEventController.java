package ru.yandex.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.error.model.ValidationException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.event.model.StateAction;
import ru.yandex.practicum.event.service.PrivateEventService;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class PrivateEventController {
    private final PrivateEventService service;

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createNewEvent(@PathVariable long userId,
                                       @Valid @RequestBody NewEventDto newEventDto) {
        validateEventDate(newEventDto.getEventDate());

        return service.createNewEvent(userId, newEventDto);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventById(@PathVariable long userId,
                                        @PathVariable long eventId,
                                        @Valid @RequestBody UpdateEventUserRequest updateEventDto) {
        if (updateEventDto.getEventDate() != null) validateEventDate(updateEventDto.getEventDate());
        if (updateEventDto.getStateAction() != null) validateStateAction(updateEventDto.getStateAction());

        return service.updateEventById(userId, eventId, updateEventDto);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateEventRequests(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @Valid @RequestBody EventRequestStatusUpdateRequest updateRequestStatusDto) {
        return service.updateEventRequests(userId, eventId, updateRequestStatusDto);
    }

    @GetMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getUserEvents(@PathVariable long userId,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getUserEvents(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getUserEventById(@PathVariable long userId,
                                         @PathVariable long eventId) {
        return service.getUserEventById(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getUserEventRequests(@PathVariable long userId,
                                                              @PathVariable long eventId) {
        return service.getUserEventRequests(userId, eventId);
    }

    private void validateEventDate(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Дата и время события должны быть через 2 часа или позже");
        }
    }

    private void validateStateAction(StateAction state) {
        if (!state.equals(StateAction.CANCEL_REVIEW) && !state.equals(StateAction.SEND_TO_REVIEW)) {
            throw new ValidationException("State action incorrect");
        }
    }
}
