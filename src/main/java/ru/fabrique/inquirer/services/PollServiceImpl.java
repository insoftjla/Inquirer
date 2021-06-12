package ru.fabrique.inquirer.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;
import ru.fabrique.inquirer.model.UserAnswer;
import ru.fabrique.inquirer.repositories.PollRepository;
import ru.fabrique.inquirer.repositories.UserAnswerRepository;

@RequiredArgsConstructor
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    @Override
    public Optional<Poll> findById(Long id) {
        return pollRepository.findById(id);
    }

    @Override
    public Optional<Poll> findByQuestion(Question question) {
        return pollRepository.findByQuestions(question);
    }

    @Override
    public Page<Poll> findAllWithPagination(Pageable pageable) {
        return pollRepository.findAll(pageable);
    }

    @Override
    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    @Override
    public void delete(Long id) {
        pollRepository.deleteById(id);
    }
}
