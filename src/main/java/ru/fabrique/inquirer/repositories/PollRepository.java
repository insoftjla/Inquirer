package ru.fabrique.inquirer.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;

@Repository
public interface PollRepository extends CrudRepository<Poll, Long> {
    Page<Poll> findAll(Pageable pageable);
    Optional<Poll> findByQuestions(Question question);
}
