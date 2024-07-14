package ru.yandex.practicum.compilation.mapper;

import ru.yandex.practicum.compilation.dto.CompilationDto;
import ru.yandex.practicum.compilation.dto.NewCompilationDto;
import ru.yandex.practicum.compilation.model.Compilation;
import ru.yandex.practicum.event.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static Compilation toCompilation(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(EventMapper.toShortDtoList(compilation.getEvents()))
                .build();
    }

    public static List<CompilationDto> compilationDtoList(List<Compilation> compilations) {
        if (compilations == null || compilations.isEmpty()) {
            return new ArrayList<>();
        }

        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}
