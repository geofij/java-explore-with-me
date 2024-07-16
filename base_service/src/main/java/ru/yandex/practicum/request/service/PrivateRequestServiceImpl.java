package ru.yandex.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.error.model.ConflictException;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.request.mapper.RequestMapper;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.request.model.RequestStatus;
import ru.yandex.practicum.request.storage.RequestRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        checkConflicts(event, user);

        Request newRequest = Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            newRequest.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);

            if (event.getConfirmedRequests() == event.getParticipantLimit()) {
                event.setAvailable(false);
            }

            eventRepository.save(event);
        } else {
            newRequest.setStatus(RequestStatus.PENDING);
        }

        return RequestMapper.toRequestDto(requestRepository.save(newRequest));
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        checkUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id-" + requestId + " not found"));

        if (request.getRequester().getId() == userId) {
            if (request.getStatus() == RequestStatus.CONFIRMED) {
                Event event = request.getEvent();
                event.setConfirmedRequests(event.getConfirmedRequests() - 1);

                if (!event.isAvailable() && event.getConfirmedRequests() < event.getParticipantLimit()) {
                    event.setAvailable(true);
                }

                eventRepository.save(event);
            }

            request.setStatus(RequestStatus.CANCELED);
            return RequestMapper.toRequestDto(requestRepository.save(request));
        } else {
            throw new ConflictException("Request canceled when user is not participating");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) {
        User user = checkUser(userId);

        return RequestMapper.toRequestDtoList(requestRepository.findAllByRequester(user));
    }

    private User checkUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id-" + userId + " not found"));
    }

    private Event checkEvent(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id-" + eventId + " not found"));
    }

    private void checkInitiator(long initiatorId, Event event) {
        if (initiatorId == event.getInitiator().getId()) {
            throw new ConflictException("Request conflict requester is initiator");
        }
    }

    private void checkConflicts(Event event, User user) {
        checkInitiator(user.getId(), event);

        if (requestRepository.findByRequester_IdAndEvent_Id(user.getId(), event.getId()).isPresent()) {
            throw new ConflictException("Request conflict request is already exist");
        }

        if (!(event.getState().equals(EventState.PUBLISHED))) {
            throw new ConflictException("Request conflict request is not published");
        }

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("Request conflict participant limit has been reached");
        }
    }
}
