package ru.yandex.practicum.comments.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
public class DeleteCommentAdminRequest {
    @NotNull
    private List<Long> commentIds;
}
