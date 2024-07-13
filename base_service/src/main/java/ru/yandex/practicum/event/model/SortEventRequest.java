package ru.yandex.practicum.event.model;

import java.util.Optional;

public enum SortEventRequest {
    EVENT_DATE,
    VIEWS;

    public static Optional<SortEventRequest> from(String s) {
        for (SortEventRequest sort : values()) {
            if (sort.name().equalsIgnoreCase(s)) {
                return Optional.of(sort);
            }
        }
        return Optional.empty();
    }
}
