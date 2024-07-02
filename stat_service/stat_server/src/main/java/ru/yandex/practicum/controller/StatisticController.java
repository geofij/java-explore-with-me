package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.EndpointHit;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.error.model.ValidationException;
import ru.yandex.practicum.service.StatisticService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class StatisticController {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final StatisticService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit addNewHit(@RequestBody @Valid EndpointHit newHit) {
        log.info("New hit {}", newHit);
        return service.addNewHit(newHit);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStats> getStat(@RequestParam @DateTimeFormat(pattern = FORMAT) LocalDateTime start,
                             @RequestParam @DateTimeFormat(pattern = FORMAT) LocalDateTime end,
                             @RequestParam(required = false) List<String> uris,
                             @RequestParam(defaultValue = "false") boolean unique) {
        if (start.isAfter(end) || start.isEqual(end)) {
            throw new ValidationException("Дата и время начала не может равняться или быть позже конца.");
        }

        log.info("Getting statistic for uris {} between {} and {} with {} unique", uris, start, end, unique);
        return service.getStat(start, end, uris, unique);
    }
}
