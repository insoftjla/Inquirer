package ru.fabrique.inquirer.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fabrique.inquirer.model.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();

    List<Question> findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndPollIdOrderByIdAsc(Date current1, Date current2, Long id);

    Optional<Question> findByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndIdAndPollId(Date current1, Date current2, Long questionId, Long PollId);
}
