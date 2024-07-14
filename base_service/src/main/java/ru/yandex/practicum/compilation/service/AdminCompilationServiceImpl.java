package ru.yandex.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.dto.UpdateCompilationRequest;
import ru.yandex.practicum.compilation.mapper.CompilationMapper;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.compilation.model.CompilationEvent;
import ru.yandex.practicum.compilation.storage.CompilationEventRepository;
import ru.yandex.practicum.compilation.storage.CompilationRepository;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.event.model.Event;
import ru.yandex.practicum.event.storage.EventRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationEventRepository compilationEventRepository;

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation newComp = compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto));

        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
            List<CompilationEvent> compEventList = new ArrayList<>();

            for (Event event : events) {
                compEventList.add(CompilationEvent.builder()
                        .event(event)
                        .compilation(newComp)
                        .build());
            }

            compilationEventRepository.saveAll(compEventList);
            newComp.setEvents(events);
        }

        return CompilationMapper.toCompilationDto(newComp);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilationById(long compId, UpdateCompilationRequest updateCompilationDto) {
        Compilation compilation = getCompilation(compId);

        if (updateCompilationDto.getTitle() != null && !updateCompilationDto.getTitle().isBlank()) {
            compilation.setTitle(updateCompilationDto.getTitle());
        }

        if (updateCompilationDto.getPinned() != null) {
            compilation.setPinned(updateCompilationDto.getPinned());
        }

        compilation = compilationRepository.save(compilation);

        if (updateCompilationDto.getEvents() != null && !updateCompilationDto.getEvents().isEmpty()) {
            compilationEventRepository.deleteByCompilation_Id(compilation.getId());

            List<Event> events = eventRepository.findAllByIdIn(updateCompilationDto.getEvents());
            List<CompilationEvent> compEventList = new ArrayList<>();

            for (Event event : events) {
                compEventList.add(CompilationEvent.builder()
                        .event(event)
                        .compilation(compilation)
                        .build());
            }

            compilationEventRepository.saveAll(compEventList);
        }

        return CompilationMapper.toCompilationDto(getCompilation(compilation.getId()));
    }

    @Override
    @Transactional
    public void deleteCompilationById(long compId) {
        getCompilation(compId);
        compilationRepository.deleteById(compId);
    }

    private Compilation getCompilation(long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id-" + compId + " not found"));
    }
}
