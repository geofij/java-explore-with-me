package ru.yandex.practicum.compilation.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin/compilations")
public class AdminCompilationController {
    @PostMapping
    public void createCompilation() {

    }

    @PatchMapping("/{compId}")
    public void updateCompilationById(@PathVariable long compId) {

    }

    @DeleteMapping("/{compId}")
    public void deleteCompilationById(@PathVariable long compId) {

    }
}
