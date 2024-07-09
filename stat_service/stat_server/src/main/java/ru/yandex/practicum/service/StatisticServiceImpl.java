package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.EndpointHit;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.mapper.StatisticMapper;
import ru.yandex.practicum.model.Statistic;
import ru.yandex.practicum.storage.StatisticRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository repository;

    @Transactional
    @Override
    public EndpointHit addNewHit(EndpointHit newHit) {
        return StatisticMapper.toEndpointHit(repository.save(StatisticMapper.toStatistic(newHit)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<Statistic> stats;
        List<ViewStats> viewStats = new ArrayList<>();

        if (uris != null && !uris.isEmpty()) {
            stats = repository.findAllByTimestampBetweenAndUriInOrderByUri(start, end, uris);
        } else {
            stats = repository.findAllByTimestampBetweenOrderByUri(start, end);
            uris = stats.stream()
                    .map(Statistic::getUri)
                    .distinct()
                    .collect(Collectors.toList());
        }

        for (String uri : uris) {
            ViewStats newViewStats = ViewStats.builder().app("ewm-main-service").uri(uri).build();

            long hits;
            if (unique) {
                hits = stats.stream()
                        .filter(stat -> stat.getUri().equals(uri))
                        .map(Statistic::getIp)
                        .distinct()
                        .count();

                newViewStats.setHits(hits);
            } else {
                hits = stats.stream()
                        .filter(stat -> stat.getUri().equals(uri))
                        .map(Statistic::getIp)
                        .count();

                newViewStats.setHits(hits);
            }

            viewStats.add(newViewStats);
        }

        return viewStats.stream()
                .sorted(Comparator.comparingLong(ViewStats::getHits).reversed())
                .collect(Collectors.toList());
    }
}
