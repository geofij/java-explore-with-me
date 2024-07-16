package ru.yandex.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.comments.dto.CommentResponseDto;
import ru.yandex.practicum.comments.dto.CommentRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/users")
public class PrivateCommentController {
    @PostMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createNewComment(@PathVariable long userId,
                                               @RequestParam("eventId") long eventId,
                                               @Valid @RequestBody CommentRequestDto newComment) {
        return null;
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateCommentById(@PathVariable long userId,
                                                @PathVariable long commentId,
                                                @Valid @RequestBody CommentRequestDto updateComment) {
        return null;
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable long userId,
                                  @PathVariable long commentId) {

    }

    //сначала новые
    @GetMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getUserComments(@PathVariable long userId,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                    @RequestParam(defaultValue = "10") @Positive int size) {
        return null;
    }
}
