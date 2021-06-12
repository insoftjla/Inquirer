package ru.fabrique.inquirer.services;

import java.util.List;
import java.util.Optional;
import ru.fabrique.inquirer.model.UserAnswer;

public interface UserAnswerService {
    List<UserAnswer> findActiveUserAnswersByUserIdAndPollId(Long userId, Long pollId);

    List<UserAnswer> findActiveUserAnswersByUsernameAndPollId(String username, Long pollId);

    List<UserAnswer> findActiveUserAnswersByUserAnonymousIdAndPollId(Long anonymousId, Long pollId);

    void saveUserAnswer(UserAnswer userAnswer);

    List<UserAnswer> findUserAnswersByUserId(Long userId);

    List<UserAnswer> findUserAnswersByUsername(String username);

    List<UserAnswer> findUserAnswersByUserAnonymousId(Long anonymousId);

    Optional<UserAnswer> getByUserIdAndQuestionId(Long userId, Long questionId);
}
