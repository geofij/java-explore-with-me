package ru.yandex.practicum.mapper;

import ru.yandex.practicum.EndpointHit;
import ru.yandex.practicum.model.Statistic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticMapper {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static EndpointHit toEndpointHit(Statistic statistic) {
        return EndpointHit.builder()
                .id(statistic.getId())
                .app(statistic.getApp())
                .ip(statistic.getIp())
                .uri(statistic.getUri())
                .timestamp(statistic.getTimestamp().format(formatter))
                .build();
    }

    public static Statistic toStatistic(EndpointHit hit) {
        return Statistic.builder()
                .app(hit.getApp())
                .ip(hit.getIp())
                .uri(hit.getUri())
                .timestamp(LocalDateTime.parse(hit.getTimestamp(), formatter))
                .build();
    }
}
