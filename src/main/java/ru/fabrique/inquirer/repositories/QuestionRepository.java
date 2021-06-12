package ru.fabrique.inquirer.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fabrique.inquirer.model.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();
    List<Question> findAllByPollIdOrderByIdAsc(Long id);
    Optional<Question> findByIdAndPollId(Long questionId, Long PollId);
}
