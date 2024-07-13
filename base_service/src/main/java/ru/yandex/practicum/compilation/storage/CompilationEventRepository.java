package ru.yandex.practicum.compilation.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.compilation.model.CompilationEvent;

public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
    void deleteByCompilation_Id(long compId);
}
