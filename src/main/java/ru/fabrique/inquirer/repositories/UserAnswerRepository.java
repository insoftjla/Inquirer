package ru.fabrique.inquirer.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fabrique.inquirer.model.UserAnswer;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {
    List<UserAnswer> findAllByUserIdAndPollId(Long userId, Long pollId);

    List<UserAnswer> findAllByUserUsernameAndPollId(String username, Long pollId);

    List<UserAnswer> findAllByUserAnonymousIdAndPollId(Long anonymousId, Long pollId);

    List<UserAnswer> findAllByUserId(Long userId);

    List<UserAnswer> findAllByUserUsername(String username);

    List<UserAnswer> findAllByUserAnonymousId(Long anonymousId);

    Optional<UserAnswer> findByUserIdAndQuestionId(Long userId, Long questionId);
}
