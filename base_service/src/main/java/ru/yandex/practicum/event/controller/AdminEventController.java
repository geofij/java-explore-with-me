package ru.yandex.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.error.model.ValidationException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.UpdateEventAdminRequest;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.event.model.StateAction;
import ru.yandex.practicum.event.service.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin/events")
public class AdminEventController {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final AdminEventService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT) LocalDateTime rangeStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT) LocalDateTime rangeEnd,
                                        @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                        @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        if (states != null) validateEventState(states);
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventById(@PathVariable long eventId,
                                        @Valid @RequestBody UpdateEventAdminRequest updateEventRequestDto) {

        if (updateEventRequestDto.getStateAction() != null) validateStateAction(updateEventRequestDto.getStateAction());
        return service.updateEventById(eventId, updateEventRequestDto);
    }

    private void validateStateAction(StateAction state) {
        if (!state.equals(StateAction.PUBLISH_EVENT) && !state.equals(StateAction.REJECT_EVENT)) {
                throw new ValidationException("State action incorrect");
            }
    }

    private void validateEventState(List<String> states) {
        for (String state : states) {
            if (!state.equals(EventState.CANCELED.toString()) &&
                    !state.equals(EventState.PUBLISHED.toString()) &&
                    !state.equals(EventState.PENDING.toString())) {
                throw new ValidationException("Event status" + state + "incorrect");
            }
        }
    }
}
