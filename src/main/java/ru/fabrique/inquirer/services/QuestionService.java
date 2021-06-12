package ru.fabrique.inquirer.services;

import java.util.List;
import java.util.Optional;
import ru.fabrique.inquirer.model.Question;

public interface QuestionService {
    Optional<Question> findByIdEndPollId(Long questionId, Long pollId);

    Optional<Question> findById(Long id);

    List<Question> findAll();

    List<Question> findAllByPollId(Long id);

    Question save(Question question);

    void deleteById(Long id);
}
