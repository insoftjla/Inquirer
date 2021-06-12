package ru.fabrique.inquirer.services;

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
    public List<UserAnswer> findUserAnswersByUserIdAndPollId(Long userId, Long pollId) {
        return userAnswerRepository.findAllByUserIdAndPollId(userId, pollId);
    }

    @Override
    public List<UserAnswer> findUserAnswersByUsernameAndPollId(String username, Long pollId) {
        return userAnswerRepository.findAllByUserUsernameAndPollId(username, pollId);
    }

    @Override
    public List<UserAnswer> findUserAnswersByUserAnonymousIdAndPollId(Long anonymousId, Long pollId) {
        return userAnswerRepository.findAllByUserAnonymousIdAndPollId(anonymousId, pollId);
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
