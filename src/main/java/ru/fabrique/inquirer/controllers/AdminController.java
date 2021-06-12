package ru.fabrique.inquirer.controllers;

import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.fabrique.inquirer.dto.PollDto;
import ru.fabrique.inquirer.dto.PollWithUserAnswerDto;
import ru.fabrique.inquirer.dto.QuestionDto;
import ru.fabrique.inquirer.exceptions.NotFoundException;
import ru.fabrique.inquirer.logics.PollLogic;
import ru.fabrique.inquirer.mappers.PollMapper;
import ru.fabrique.inquirer.mappers.QuestionMapper;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;
import ru.fabrique.inquirer.services.PollService;
import ru.fabrique.inquirer.services.QuestionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PollLogic pollLogic;
    private final PollService pollService;
    private final QuestionService questionService;
    private final PollMapper pollMapper;
    private final QuestionMapper questionMapper;

    @GetMapping("/polls")
    public List<PollDto> getAllPollWithPagination(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sort
    ) {
        return pollMapper.toDtos(pollLogic.getAllPollsWithPagination(page, size, sort));
    }

    @PostMapping("/polls")
    @ResponseStatus(HttpStatus.CREATED)
    public PollDto createPoll(@RequestBody Poll poll) {
        return pollMapper.toDto(pollLogic.createPoll(poll));
    }

    @PutMapping("/polls/{id}")
    public PollDto updatePoll(@PathVariable Long id, @RequestBody Poll newPoll) {
        return pollMapper.toDto(pollLogic.updatePoll(id, newPoll));
    }

    @DeleteMapping("/polls/{id}")
    public void deletePoll(@PathVariable Long id) {
        pollLogic.deletePoll(id);
    }

    @PostMapping("/polls/{id}/question")
    public PollDto addQuestionToPoll(@PathVariable Long id, @RequestBody QuestionDto questionDto) {
        return pollMapper.toDto(pollLogic.addQuestion(id, questionMapper.toEntity(questionDto)));
    }

    @GetMapping("/polls/user/{userId}")
    public List<PollWithUserAnswerDto> getPollsPassedByUserId(@PathVariable Long userId) {
        return pollLogic.getPollsPassedByUserId(userId);
    }

    @GetMapping("/polls/user")
    public List<PollWithUserAnswerDto> getPollsPassedByAnonymousId(Principal principal) {
        return pollLogic.getPollsPassedByUsername(principal.getName());
    }

    @GetMapping("questions/{id}")
    private QuestionDto getQuestionById(@PathVariable Long id) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        return questionMapper.toDto(question);
    }

    @PutMapping("questions/{id}")
    public QuestionDto updateQuestion(@PathVariable Long id, @RequestBody Question newQuestion) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        question.setText(newQuestion.getText());
        question.setType(newQuestion.getType());
        question.setAnswers(newQuestion.getAnswers());
        return questionMapper.toDto(questionService.save(question));
    }

    @DeleteMapping("questions/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        Poll poll = pollService.findByQuestion(question).orElseThrow(NotFoundException::new);
        if (!poll.removeQuestion(question)) {
            throw new NotFoundException();
        }
        pollService.save(poll);
    }
}
