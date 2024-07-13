package ru.yandex.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.event.model.EventState;

import java.time.LocalDateTime;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
public class UpdateEventUserRequest {
    private String annotation;
    private Long category;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime eventDate;

    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private EventState state;
    private String title;
}
