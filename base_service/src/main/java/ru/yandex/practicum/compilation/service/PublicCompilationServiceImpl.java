package ru.yandex.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.storage.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService  {
    private final CompilationRepository repository;

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        return null;
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        return null;
    }
}
