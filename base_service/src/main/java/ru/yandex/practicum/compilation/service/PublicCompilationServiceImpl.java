package ru.yandex.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.mapper.CompilationMapper;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.compilation.storage.CompilationRepository;
import ru.yandex.practicum.error.model.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService  {
    private final CompilationRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        List<Compilation> compilations;

        if (pinned != null) {
            compilations = repository.findAllByPinned(pinned, page);
        } else {
            compilations = repository.findAllComp(page);
        }

        return CompilationMapper.compilationDtoList(compilations);
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(long compId) {
        Compilation compilation = checkCompilation(compId);

        return CompilationMapper.toCompilationDto(compilation);
    }

    private Compilation checkCompilation(long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id-" + compId + " not found"));
    }
}
