package ru.yandex.practicum.comments.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank
    @Size(min = 1, max = 1024)
    private String text;
}
