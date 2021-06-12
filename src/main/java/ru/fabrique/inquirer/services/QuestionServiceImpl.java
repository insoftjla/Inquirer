package ru.fabrique.inquirer.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fabrique.inquirer.model.Question;
import ru.fabrique.inquirer.repositories.QuestionRepository;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Optional<Question> findActiveByIdEndPollId(Long questionId, Long pollId) {
        Date currentDate = new Date();
        return questionRepository.findByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndIdAndPollId(
                currentDate, currentDate, questionId, pollId);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findAllActiveByPollId(Long id) {
        Date currentDate = new Date();
        return questionRepository.findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndPollIdOrderByIdAsc(currentDate, currentDate, id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }
}
