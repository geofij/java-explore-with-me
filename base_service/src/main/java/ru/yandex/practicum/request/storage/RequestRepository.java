package ru.yandex.practicum.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.request.model.Request;
import ru.yandex.practicum.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRequester_IdAndEvent_Id(long requesterId, long eventId);

    List<Request> findAllByRequester(User user);
}
