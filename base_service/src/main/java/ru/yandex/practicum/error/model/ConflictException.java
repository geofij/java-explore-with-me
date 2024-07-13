package ru.yandex.practicum.error.model;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
