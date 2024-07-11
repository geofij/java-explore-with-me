package ru.yandex.practicum.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
}
