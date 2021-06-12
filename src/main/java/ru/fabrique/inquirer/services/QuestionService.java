package ru.fabrique.inquirer.services;

import java.util.List;
import java.util.Optional;
import ru.fabrique.inquirer.model.Question;

public interface QuestionService {
    Optional<Question> findActiveByIdEndPollId(Long questionId, Long pollId);

    Optional<Question> findById(Long id);

    List<Question> findAllActiveByPollId(Long id);

    Question save(Question question);
}
