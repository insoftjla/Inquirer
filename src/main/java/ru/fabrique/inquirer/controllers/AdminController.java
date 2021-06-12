package ru.fabrique.inquirer.controllers;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Get all polls with pagination")
    @GetMapping("/polls")
    public List<PollDto> getAllPollWithPagination(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sort
    ) {
        return pollMapper.toDtos(pollLogic.getAllPollsWithPagination(page, size, sort));
    }

    @ApiOperation("Create poll")
    @PostMapping("/polls")
    @ResponseStatus(HttpStatus.CREATED)
    public PollDto createPoll(@RequestBody Poll poll) {
        return pollMapper.toDto(pollLogic.createPoll(poll));
    }

    @ApiOperation("Update poll")
    @PutMapping("/polls/{id}")
    public PollDto updatePoll(@PathVariable Long id, @RequestBody Poll newPoll) {
        return pollMapper.toDto(pollLogic.updatePoll(id, newPoll));
    }

    @ApiOperation("Delete poll")
    @DeleteMapping("/polls/{id}")
    public void deletePoll(@PathVariable Long id) {
        pollLogic.deletePoll(id);
    }

    @ApiOperation("Add question to poll")
    @PostMapping("/polls/{id}/question")
    public PollDto addQuestionToPoll(@PathVariable Long id, @RequestBody QuestionDto questionDto) {
        return pollMapper.toDto(pollLogic.addQuestion(id, questionMapper.toEntity(questionDto)));
    }

    @ApiOperation(value = "Get completed polls with details by user ID")
    @GetMapping("/polls/user/{userId}")
    public List<PollWithUserAnswerDto> getPollsPassedByUserId(@PathVariable Long userId) {
        return pollLogic.getPollsPassedByUserId(userId);
    }

    @ApiOperation(value = "Get completed polls with details, for current authorized user")
    @GetMapping("/polls/user")
    public List<PollWithUserAnswerDto> getPollsPassedByAnonymousId(Principal principal) {
        return pollLogic.getPollsPassedByUsername(principal.getName());
    }

    @ApiOperation(value = "Get question by id")
    @GetMapping("questions/{id}")
    private QuestionDto getQuestionById(@PathVariable Long id) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        return questionMapper.toDto(question);
    }

    @ApiOperation(value = "Update question by id")
    @PutMapping("questions/{id}")
    public QuestionDto updateQuestion(@PathVariable Long id, @RequestBody Question newQuestion) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        question.setText(newQuestion.getText());
        question.setType(newQuestion.getType());
        question.setAnswers(newQuestion.getAnswers());
        return questionMapper.toDto(questionService.save(question));
    }

    @ApiOperation(value = "Delete question by id")
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
