package ru.yandex.practicum.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Location {
    private double lat;
    private double lon;
}
