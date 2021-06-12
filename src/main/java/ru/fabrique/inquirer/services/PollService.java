package ru.fabrique.inquirer.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;

public interface PollService {
    Optional<Poll> findById(Long id);

    Optional<Poll> findByQuestion(Question question);

    Page<Poll> findAllWithPagination(Pageable pageable);

    Poll save(Poll poll);

    void delete(Long id);
}
