package ru.yandex.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.service.PublicCommentService;

import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class PublicCommentController {
    private final PublicCommentService service;

    //сортировка по времени создания: сначала старые, сначала новые. по умолчанию сначала старые OLD/NEW
    @GetMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getEventComments(@PathVariable long eventId,
                                                     @RequestParam(defaultValue = "OLD") String sort,
                                                     @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                     @RequestParam(defaultValue = "10") @Positive int size) {
        if (!sort.equals("OLD") && !sort.equals("NEW")) {
            throw new ValidationException("Incorrect sorting");
        }

        return service.getEventComments(eventId, sort, from, size);
    }
}
