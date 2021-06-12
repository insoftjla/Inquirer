package ru.fabrique.inquirer.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fabrique.inquirer.model.UserAnswer;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {
    List<UserAnswer> findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndUserIdAndPollId(Date current1, Date current2, Long userId, Long pollId);

    List<UserAnswer> findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndUserUsernameAndPollId(Date current1, Date current2, String username, Long pollId);

    List<UserAnswer> findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndUserAnonymousIdAndPollId(Date current1, Date current2, Long anonymousId, Long pollId);

    List<UserAnswer> findAllByUserId(Long userId);

    List<UserAnswer> findAllByUserUsername(String username);

    List<UserAnswer> findAllByUserAnonymousId(Long anonymousId);

    Optional<UserAnswer> findByUserIdAndQuestionId(Long userId, Long questionId);
}
