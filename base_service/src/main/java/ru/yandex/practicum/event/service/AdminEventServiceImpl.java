package ru.yandex.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.UpdateEventAdminRequest;
import ru.yandex.practicum.event.mapper.EventMapper;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.event.specification.EventSpecification;
import ru.yandex.practicum.event.storage.EventRepository;
import ru.yandex.practicum.user.model.User;
import ru.yandex.practicum.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventFullDto> getEvents(List<Long> users,
                                        List<String> states,
                                        List<Long> categories,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        int from,
                                        int size) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        
        Specification<Event> spec = Specification.where(null);

        if (users != null && !users.isEmpty()) {
            List<User> userList = users.stream().map(user -> userRepository.findById(user).orElseThrow(() ->
                    new NotFoundException("User not found"))).collect(Collectors.toList());
            spec = spec.and(EventSpecification.byInitiatorIn(userList));
        }
        if (states != null && !states.isEmpty()) {
            List<EventState> stateList = fromStringsToEventStates(states);
            spec = spec.and(EventSpecification.byStateIn(stateList));
        }
        if (categories != null && !categories.isEmpty()) {
            List<Category> categoriesList = categories.stream().map(category -> categoryRepository.findById(category)
                    .orElseThrow(() -> new NotFoundException("Category not found"))).collect(Collectors.toList());
            spec = spec.and(EventSpecification.byCategoryIn(categoriesList));
        }
        if (rangeStart != null && rangeEnd != null) {
                spec = spec.and(EventSpecification.byEventDateBetween(rangeStart, rangeEnd));
        }

        List<Event> events = eventRepository.findAll(spec, page).getContent();

        return EventMapper.toFullDtoList(events);
    }

    @Override
    public EventFullDto updateEventById(long eventId, UpdateEventAdminRequest updateEventRequestDto) {
        return null;
    }
    
    private List<EventState> fromStringsToEventStates(List<String> states) {
        List<EventState> eventStates = new ArrayList<>();

        for (String state : states) {
            switch (state) {
                case ("CANCELED"):
                    eventStates.add(EventState.CANCELED);
                    break;
                case ("PENDING"):
                    eventStates.add(EventState.PENDING);
                    break;
                case ("PUBLISHED"):
                    eventStates.add(EventState.PUBLISHED);
                    break;
            }
        }
        
        return eventStates;
    }
}
