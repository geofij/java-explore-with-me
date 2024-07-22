package ru.yandex.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.dto.DeleteCommentAdminRequest;
import ru.yandex.practicum.comments.service.AdminCommentService;
import ru.yandex.practicum.error.model.BadRequestException;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final AdminCommentService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getComments(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                @RequestParam(defaultValue = "10") @Positive int size,
                                                @RequestParam(required = false) List<Long> eventIds,
                                                @RequestParam(required = false) List<Long> userIds,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT) LocalDateTime rangeStart,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = FORMAT) LocalDateTime rangeEnd,
                                                @RequestParam(required = false) Boolean isUpdated,
                                                @RequestParam(defaultValue = "NEW") String sort) {
        if (sort != null && !sort.equals("OLD") && !sort.equals("NEW")) {
            throw new ValidationException("Incorrect sorting");
        }

        if ((rangeStart != null) && (rangeEnd != null)) if (rangeStart.isAfter(rangeEnd))
            throw new BadRequestException("Start date and end date incorrect");

        return service.getComments(from, size, eventIds, userIds, rangeStart, rangeEnd, isUpdated, sort);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComments(@Valid @RequestBody @NotNull DeleteCommentAdminRequest deleteCommentAdminRequest) {
        if (deleteCommentAdminRequest.getCommentIds().isEmpty()) {
            throw new ValidationException("Empty comment ids for deleting");
        }

        service.deleteComments(deleteCommentAdminRequest);
    }
}
