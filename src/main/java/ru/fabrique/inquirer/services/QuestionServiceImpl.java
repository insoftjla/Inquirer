package ru.fabrique.inquirer.services;

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
    public Optional<Question> findByIdEndPollId(Long questionId, Long pollId) {
        return questionRepository.findByIdAndPollId(questionId, pollId);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> findAllByPollId(Long id) {
        return questionRepository.findAllByPollIdOrderByIdAsc(id);
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
