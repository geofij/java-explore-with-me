package ru.yandex.practicum.service;

import ru.yandex.practicum.EndpointHit;
import ru.yandex.practicum.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    public EndpointHit addNewHit(EndpointHit newHit);

    public List<ViewStats> getStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
