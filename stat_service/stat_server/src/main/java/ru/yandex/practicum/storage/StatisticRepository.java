package ru.yandex.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    public List<Statistic> findAllByTimestampBetweenAndUriInOrderByUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    public List<Statistic> findAllByTimestampBetweenOrderByUri(LocalDateTime start, LocalDateTime end);
}
