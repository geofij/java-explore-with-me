package ru.yandex.practicum.compilation.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.yandex.practicum.event.model.Event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Table(name = "compilations")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Entity
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private Boolean pinned;

    @ManyToMany
    @JoinTable(name = "compilation_event",
                joinColumns = { @JoinColumn(name = "compilation_id") },
                inverseJoinColumns = { @JoinColumn(name = "event_id") })
    private List<Event> events = new ArrayList<>();
}
