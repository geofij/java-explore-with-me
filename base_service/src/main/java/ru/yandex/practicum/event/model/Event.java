package ru.yandex.practicum.event.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.category.model.Category;
import ru.yandex.practicum.user.model.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Table(name = "events")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String annotation;

    @ManyToOne
    private Category category;

    private long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;

    @ManyToOne
    private User initiator;

    private double lat;
    private double lon;
    private boolean paid;
    private long participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private EventState state;

    private String title;
    private long views;
}
