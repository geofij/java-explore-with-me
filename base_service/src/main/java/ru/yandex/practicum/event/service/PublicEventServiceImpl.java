package ru.yandex.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.EndpointHit;
import ru.yandex.practicum.StatisticClient;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.category.storage.CategoryRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.dto.EventFullDto;
import ru.yandex.practicum.event.dto.EventShortDto;
import ru.yandex.practicum.event.mapper.EventMapper;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.model.EventState;
import ru.yandex.practicum.event.model.SortEventRequest;
import ru.yandex.practicum.event.specification.EventSpecification;
import ru.yandex.practicum.event.storage.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final StatisticClient statisticClient;

    @Override
    @Transactional
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest httpRequest) {
        Specification<Event> spec = Specification.where(null);
        Optional<SortEventRequest> sortRequest = SortEventRequest.from(sort);

        Sort sortBy = sortRequest.map(request -> request == SortEventRequest.VIEWS ? Sort.by(Sort.Direction.DESC, "views") :
                Sort.by(Sort.Direction.DESC, "eventDate")).orElseGet(() -> Sort.by(Sort.Direction.DESC, "id"));
        Pageable page = PageRequest.of(from, size, sortBy);


        if (text != null && !text.isEmpty()) {
            spec = spec.and(EventSpecification.byAnnotation(text));
        }
        if (categories != null && !categories.isEmpty()) {
            List<Category> categoriesList = categoryRepository.findAllByIdIn(categories);
            spec = spec.and(EventSpecification.byCategoryIn(categoriesList));
        }
        if (paid != null) {
            spec = spec.and(EventSpecification.byPaid(paid));
        }
        if (onlyAvailable != null && onlyAvailable) {
            spec = spec.and(EventSpecification.byAvailable(true));
        }

        spec = spec.and(EventSpecification.byStateIn(List.of(EventState.PUBLISHED)));

        List<Event> eventPage = eventRepository.findAll(spec, page).getContent();

        for (Event event : eventPage) {
            event.setViews(event.getViews() + 1);
        }

        eventRepository.saveAll(eventPage);
        statisticClient.post(EndpointHit.builder()
                .uri(httpRequest.getRequestURI())
                .app("ewm-main-service")
                .ip(httpRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build());
        return EventMapper.toShortDtoList(eventPage);
    }

    @Override
    @Transactional
    public EventFullDto getEventById(long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Event with id " + eventId + " not found");
        }

        event.setViews(event.getViews() + 1);
        eventRepository.save(event);

        statisticClient.post(EndpointHit.builder()
                        .uri(request.getRequestURI())
                        .app("ewm-main-service")
                        .ip(request.getRemoteAddr())
                        .timestamp(LocalDateTime.now().format(FORMATTER))
                .build());
        return EventMapper.toFullDto(event);
    }
}
