package ru.yandex.practicum.compilation.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(boolean pinned, Pageable page);

    @Query("select c from Compilation c")
    List<Compilation> findAllComp(Pageable page);
}
