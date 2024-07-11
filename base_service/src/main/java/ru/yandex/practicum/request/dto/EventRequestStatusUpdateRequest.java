package ru.yandex.practicum.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.request.model.RequestStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;

    @NotNull
    private RequestStatus status;
}
