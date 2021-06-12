package ru.fabrique.inquirer.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fabrique.inquirer.model.UserAnswer;
import ru.fabrique.inquirer.repositories.UserAnswerRepository;

@RequiredArgsConstructor
@Service
public class UserAnswerServiceImpl implements UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    @Override
    public List<UserAnswer> findActiveUserAnswersByUserIdAndPollId(Long userId, Long pollId) {
        Date currentDate = new Date();
        return userAnswerRepository.findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndUserIdAndPollId(
                currentDate, currentDate, userId, pollId);
    }

    @Override
    public List<UserAnswer> findActiveUserAnswersByUsernameAndPollId(String username, Long pollId) {
        Date currentDate = new Date();
        return userAnswerRepository.findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndUserUsernameAndPollId(
                currentDate, currentDate, username, pollId);
    }

    @Override
    public List<UserAnswer> findActiveUserAnswersByUserAnonymousIdAndPollId(Long anonymousId, Long pollId) {
        Date currentDate = new Date();
        return userAnswerRepository.findAllByPollDateStartLessThanEqualAndPollDateEndGreaterThanEqualAndUserAnonymousIdAndPollId(
                currentDate, currentDate, anonymousId, pollId);
    }

    @Override
    public void saveUserAnswer(UserAnswer userAnswer) {
        userAnswerRepository.save(userAnswer);
    }

    @Override
    public List<UserAnswer> findUserAnswersByUserId(Long userId) {
        return userAnswerRepository.findAllByUserId(userId);
    }

    @Override
    public List<UserAnswer> findUserAnswersByUsername(String username) {
        return userAnswerRepository.findAllByUserUsername(username);
    }

    @Override
    public List<UserAnswer> findUserAnswersByUserAnonymousId(Long anonymousId) {
        return userAnswerRepository.findAllByUserAnonymousId(anonymousId);
    }

    @Override
    public Optional<UserAnswer> getByUserIdAndQuestionId(Long userId, Long questionId) {
        return userAnswerRepository.findByUserIdAndQuestionId(userId, questionId);
    }
}
