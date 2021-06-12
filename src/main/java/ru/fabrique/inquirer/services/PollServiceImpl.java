package ru.fabrique.inquirer.services;

import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;
import ru.fabrique.inquirer.repositories.PollRepository;

@RequiredArgsConstructor
@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    @Override
    public Optional<Poll> findActiveById(Long id) {
        Date currentDate = new Date();
        return pollRepository.findByDateStartLessThanEqualAndDateEndGreaterThanEqualAndId(currentDate, currentDate, id);
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

    @Override
    public Page<Poll> findAllActiveWithPagination(Pageable pageable) {
        Date currentDate = new Date();
        return pollRepository.findAllByDateStartLessThanEqualAndDateEndGreaterThanEqual(currentDate, currentDate, pageable);
    }
}
