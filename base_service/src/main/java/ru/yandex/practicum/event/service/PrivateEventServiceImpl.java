package ru.yandex.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.ConflictException;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.event.mapper.EventMapper;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.event.model.StateAction;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.mapper.RequestMapper;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.request.model.RequestStatus;
import ru.yandex.practicum.request.storage.RequestRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    @Transactional
    public EventFullDto createNewEvent(long userId, NewEventDto newEventDto) {
        User user = checkUser(userId);
        Category category = checkCategory(newEventDto.getCategory());

        return EventMapper.toFullDto(eventRepository.save(EventMapper.toEvent(newEventDto, category, user)));
    }

    @Override
    @Transactional
    public EventFullDto updateEventById(long userId, long eventId, UpdateEventUserRequest updateEventDto) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        checkInitiator(userId, event);

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Only pending or canceled events can be changed");
        }

        if (updateEventDto.getAnnotation() != null) event.setAnnotation(updateEventDto.getAnnotation());

        if (updateEventDto.getCategory() != null) {
            Category category = checkCategory(updateEventDto.getCategory());
            event.setCategory(category);
        }

        if (updateEventDto.getDescription() != null && !updateEventDto.getDescription().isBlank()) event.setDescription(updateEventDto.getDescription());
        if (updateEventDto.getEventDate() != null) event.setEventDate(updateEventDto.getEventDate());

        if (updateEventDto.getLocation() != null) {
            event.setLat(updateEventDto.getLocation().getLat());
            event.setLon(updateEventDto.getLocation().getLon());
        }

        if (updateEventDto.getPaid() != null) event.setPaid(updateEventDto.getPaid());
        if (updateEventDto.getParticipantLimit() != null) event.setParticipantLimit(updateEventDto.getParticipantLimit());
        if (updateEventDto.getRequestModeration() != null) event.setRequestModeration(updateEventDto.getRequestModeration());
        if (updateEventDto.getTitle() != null && !updateEventDto.getTitle().isBlank()) event.setTitle(updateEventDto.getTitle());

        if (updateEventDto.getStateAction() != null) {

            if (updateEventDto.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            } else if (updateEventDto.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            }
        }

        return EventMapper.toFullDto(eventRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequests(long userId, long eventId, EventRequestStatusUpdateRequest updateRequestStatusDto) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        checkInitiator(userId, event);

        EventRequestStatusUpdateResult result = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(new ArrayList<>())
                .rejectedRequests(new ArrayList<>())
                .build();
        long countConfirmedRequests = event.getConfirmedRequests();

        for (Long requestId : updateRequestStatusDto.getRequestIds()) {
            Request request = requestRepository.findById(requestId).orElseThrow(() ->
                    new NotFoundException("Request with id " + requestId + " не not found"));

            if (request.getEvent().getId() != eventId)
                throw new NotFoundException("Request with id " + requestId + " не not found");
            if (!request.getStatus().equals(RequestStatus.PENDING))
                throw new ConflictException("Request with id- " + request.getId() + "is already has already been processed");

            if (updateRequestStatusDto.getStatus().equals(RequestStatus.CONFIRMED) &&
                    (countConfirmedRequests < event.getParticipantLimit() || event.getParticipantLimit() == 0)) {
                request.setStatus(RequestStatus.CONFIRMED);
                requestRepository.save(request);

                result.getConfirmedRequests().add(RequestMapper.toRequestDto(request));
                ++countConfirmedRequests;
            } else {
                request.setStatus(RequestStatus.REJECTED);
                requestRepository.save(request);
                result.getRejectedRequests().add(RequestMapper.toRequestDto(request));
            }
        }

        if (event.getConfirmedRequests() < countConfirmedRequests) {
            event.setConfirmedRequests(countConfirmedRequests);
            eventRepository.save(event);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getUserEvents(long userId, int from, int size) {
        checkUser(userId);

        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);

        return EventMapper.toShortDtoList(eventRepository.findAllByInitiator_Id(userId, page));
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getUserEventById(long userId, long eventId) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        checkInitiator(userId, event);

        return EventMapper.toFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getUserEventRequests(long userId, long eventId) {
        checkUser(userId);
        Event event = checkEvent(eventId);
        checkInitiator(userId, event);

        List<Request> requests = requestRepository.findAllByEvent_Id(eventId);
        return RequestMapper.toRequestDtoList(requests);
    }

    private User checkUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    private Category checkCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found"));
    }

    private Event checkEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Category with id " + eventId + " not found"));
    }

    private void checkInitiator(long userId, Event event) {
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException("Event with id-" + event.getId() + " where user id-" + userId + " not found");
        }
    }
}
