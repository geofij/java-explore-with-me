package ru.yandex.practicum.error;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.error.model.BadRequestException;
import ru.yandex.practicum.error.model.ConflictException;
import ru.yandex.practicum.error.model.NotFoundException;
import ru.yandex.practicum.error.model.ValidationException;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadArgumentException(final MethodArgumentNotValidException e) {
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(final BadRequestException e) {
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason(e.getLocalizedMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final ValidationException e) {
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason(e.getMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) { // Element not found
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .reason(e.getMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        return ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .reason(e.getLocalizedMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleNoBody(final HttpMessageNotReadableException e) {
        return ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .reason(e.getLocalizedMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }

    @ExceptionHandler({ConstraintViolationException.class,
                        AccessDeniedException.class,
                        DataIntegrityViolationException.class,
                        DataAccessException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleOtherConflictException(Exception e) {
        return ApiError.builder()
                .status(HttpStatus.CONFLICT.toString())
                .reason(e.getLocalizedMessage())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(FORMATTER))
                .build();
    }
}
