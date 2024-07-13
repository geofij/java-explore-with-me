package ru.yandex.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.dto.NewEventDto;
import ru.yandex.practicum.event.dto.UpdateEventUserRequest;
import ru.yandex.practicum.event.mapper.EventMapper;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.yandex.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.yandex.practicum.request.dto.ParticipationRequestDto;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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
        return null;
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateEventRequests(long userId, long eventId, EventRequestStatusUpdateRequest updateRequestStatusDto) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getUserEvents(long userId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getUserEventById(long userId, long eventId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getUserEventRequests(long userId, long eventId) {
        return null;
    }

    private User checkUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    private Category checkCategory(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found"));
    }
}
