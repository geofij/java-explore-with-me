package ru.yandex.practicum;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private long hits;
}
