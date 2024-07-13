package ru.yandex.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.StatisticClient;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.mapper.EventMapper;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final StatisticClient statisticClient;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size) {
//        Specification<Event> spec = Specification.where(null);
//        List<Category> categoriesList;
//        Sort sortBy;
//        Optional<SortEventRequest> sortRequest = SortEventRequest.from(sort);
//
//        sortBy = sortRequest.map(request -> request == SortEventRequest.VIEWS ? Sort.by(Sort.Direction.DESC, "views") :
//                Sort.by(Sort.Direction.DESC, "eventDate")).orElseGet(() -> Sort.by(Sort.Direction.DESC, "id"));
//        Pageable page = PageRequest.of(from, size, sortBy);
//        Page<Event> eventPage;
//
//        if (text != null && !text.isEmpty()) {
//            spec = spec.and(EventSpecification.byAnnotation(text));
//        }
//        if (categories != null && !categories.isEmpty()) {
//            categoriesList = categoryRepository.findAllIdIn(categories);
//            spec = spec.and(EventSpecification.byCategoryIn(categoriesList));
//        }
//        if (paid != null) {
//            spec = spec.and(EventSpecification.byPaid(paid));
//        }
//        if (onlyAvailable != null && onlyAvailable) {
//            spec = spec.and(EventSpecification.byAvailable(true));
//        }
//
//        eventPage = eventRepository.findAll(spec, page);

        return null;
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Category with id " + eventId + " not found"));

        return EventMapper.toFullDto(event);
    }
}
