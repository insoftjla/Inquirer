package ru.fabrique.inquirer.logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.fabrique.inquirer.dto.AnswerDto;
import ru.fabrique.inquirer.dto.PollWithUserAnswerDto;
import ru.fabrique.inquirer.dto.QuestionDto;
import ru.fabrique.inquirer.dto.UserAnswerDto;
import ru.fabrique.inquirer.exceptions.AlreadyReportedException;
import ru.fabrique.inquirer.exceptions.NotFoundException;
import ru.fabrique.inquirer.mappers.AnswerMapper;
import ru.fabrique.inquirer.mappers.QuestionMapper;
import ru.fabrique.inquirer.model.Answer;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;
import ru.fabrique.inquirer.model.QuestionType;
import ru.fabrique.inquirer.model.User;
import ru.fabrique.inquirer.model.UserAnswer;
import ru.fabrique.inquirer.services.PollService;
import ru.fabrique.inquirer.services.QuestionService;
import ru.fabrique.inquirer.services.UserAnswerService;
import ru.fabrique.inquirer.services.UserService;

@RequiredArgsConstructor
@Component
public class PollLogic {

    private final PollService pollService;
    private final QuestionService questionService;
    private final UserService userService;
    private final UserAnswerService userAnswerService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    @Transactional
    public Poll createPoll(Poll poll) {
        return pollService.save(poll);
    }

    @Transactional(readOnly = true)
    public Poll getPollById(Long pollId) {
        return pollService.findById(pollId).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Poll> getPollsWithPagination(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Poll> pollPage = pollService.findAllWithPagination(pageable);
        if (pollPage.isEmpty()) {
            throw new NotFoundException();
        }
        return pollPage.toList();
    }

    @Transactional
    public Poll updatePoll(Long pollId, Poll newPoll) {
        Poll poll = pollService.findById(pollId).orElseThrow(NotFoundException::new);
        poll.setName(newPoll.getName());
        poll.setDateEnd(newPoll.getDateEnd());
        poll.setDescription(newPoll.getDescription());
        poll.setDateEnd(newPoll.getDateEnd());
        return pollService.save(poll);
    }

    @Transactional
    public void deletePoll(Long pollId) {
        pollService.delete(pollId);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userService.findByUsername(username).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public User saveAnonymousUserIfNotPresent(Long anonymousId) {
        return userService.findByUserAnonymousId(anonymousId)
                .orElseGet(() -> userService.save(new User(anonymousId)));
    }

    @Transactional
    public Poll addQuestion(Long pollId, Question question) {
        Poll poll = pollService.findById(pollId).orElseThrow(NotFoundException::new);
        poll.addQuestion(question);
        return pollService.save(poll);
    }

    @Transactional
    public void saveUserAnswer(Long pollId, User user, UserAnswerDto userAnswerDto) {
        if (userAnswerService.getByUserIdAndQuestionId(user.getId(), userAnswerDto.getQuestionId()).isPresent()) {
            throw new AlreadyReportedException();
        }
        Question question = questionService.findByIdEndPollId(userAnswerDto.getQuestionId(), pollId).orElseThrow(NotFoundException::new);
        UserAnswer userAnswer = new UserAnswer();
        if (question.getType().equals(QuestionType.RADIO)) {
            userAnswer.getAnswers().add(getOneAnswer(question, userAnswerDto));
        }
        if (question.getType().equals(QuestionType.CHECKBOX)) {
            userAnswer.getAnswers().addAll(getAllAnswer(question, userAnswerDto));
        }
        if (question.getType().equals(QuestionType.TEXT)) {
            Answer newAnswer = new Answer();
            newAnswer.setText(userAnswerDto.getText());
            userAnswer.getAnswers().add(newAnswer);
        }
        userAnswer.setUser(user);
        userAnswer.setPoll(question.getPoll());
        userAnswer.setQuestion(question);
        userAnswerService.saveUserAnswer(userAnswer);
    }

    @Transactional(readOnly = true)
    public Question getNextQuestionByUserIdAndPollId(Long userId, Long pollId) {
        return getNextQuestionByUserAnswers(pollId, userAnswerService.findUserAnswersByUserIdAndPollId(userId, pollId));
    }

    @Transactional(readOnly = true)
    public Question getNextQuestionByUsernameAndPollId(String username, Long pollId) {
        return getNextQuestionByUserAnswers(pollId, userAnswerService.findUserAnswersByUsernameAndPollId(username, pollId));
    }

    @Transactional(readOnly = true)
    public Question getNextQuestionByUserAnonymousIdAndPollId(Long anonymousId, Long pollId) {
        return getNextQuestionByUserAnswers(pollId, userAnswerService.findUserAnswersByUserAnonymousIdAndPollId(anonymousId, pollId));
    }

    @Transactional(readOnly = true)
    public List<PollWithUserAnswerDto> getPollsPassedByUserId(Long userId) {
        return mappedPollByUserAnswers(userAnswerService.findUserAnswersByUserId(userId));
    }

    @Transactional(readOnly = true)
    public List<PollWithUserAnswerDto> getPollsPassedByUsername(String username) {
        return mappedPollByUserAnswers(userAnswerService.findUserAnswersByUsername(username));
    }

    @Transactional(readOnly = true)
    public List<PollWithUserAnswerDto> getPollsPassedByAnonymousId(Long anonymousId) {
        return mappedPollByUserAnswers(userAnswerService.findUserAnswersByUserAnonymousId(anonymousId));
    }

    private Question getNextQuestionByUserAnswers(Long pollId, List<UserAnswer> userAnswers) {
        List<Question> questions = questionService.findAllByPollId(pollId);
        for (Question question : questions) {
            if (userAnswers.stream()
                    .noneMatch((a) -> a.getQuestion().equals(question))) {
                return question;
            }
        }
        throw new NotFoundException();
    }

    private Answer getOneAnswer(Question question, UserAnswerDto userAnswerDto) {
        return question.getAnswers().stream()
                .filter(a -> a.getId().equals(userAnswerDto.getAnswerIds().get(0)))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    private List<Answer> getAllAnswer(Question question, UserAnswerDto userAnswerDto) {
        List<Answer> result = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            if (userAnswerDto.getAnswerIds().contains(answer.getId())) {
                result.add(answer);
            }
        }
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    private List<PollWithUserAnswerDto> mappedPollByUserAnswers(List<UserAnswer> userAnswers) {
        Map<Poll, List<UserAnswer>> userAnswersByPoll = new HashMap<>();
        for (UserAnswer userAnswer : userAnswers) {
            userAnswersByPoll.putIfAbsent(userAnswer.getPoll(), new ArrayList<>());
            userAnswersByPoll.computeIfPresent(userAnswer.getPoll(), (k, v) -> {
                v.add(userAnswer);
                return v;
            });
        }
        List<PollWithUserAnswerDto> result = new ArrayList<>(userAnswersByPoll.size());
        for (Map.Entry<Poll, List<UserAnswer>> entry : userAnswersByPoll.entrySet()) {
            Map<QuestionDto, List<AnswerDto>> answersByQuestions = new HashMap<>();
            for (UserAnswer userAnswer : entry.getValue()) {
                answersByQuestions.putIfAbsent(questionMapper.toDto(userAnswer.getQuestion()), answerMapper.toDtos(userAnswer.getAnswers()));
            }
            PollWithUserAnswerDto pollWithUserAnswerDto = PollWithUserAnswerDto.toDto(entry.getKey());
            pollWithUserAnswerDto.setQuestions(answersByQuestions);
            result.add(pollWithUserAnswerDto);
        }
        return result;
    }
}
