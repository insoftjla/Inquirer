package ru.fabrique.inquirer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fabrique.inquirer.dto.QuestionDto;
import ru.fabrique.inquirer.exceptions.NotFoundException;
import ru.fabrique.inquirer.mappers.QuestionMapper;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.Question;
import ru.fabrique.inquirer.services.PollService;
import ru.fabrique.inquirer.services.QuestionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("question/{id}")
public class QuestionController {

    private final PollService pollService;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping
    private QuestionDto getById(@PathVariable Long id) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        return questionMapper.toDto(question);
    }

    @PutMapping
    public QuestionDto update(@PathVariable Long id, @RequestBody Question newQuestion) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        question.setText(newQuestion.getText());
        question.setType(newQuestion.getType());
        question.setAnswers(newQuestion.getAnswers());
        return questionMapper.toDto(questionService.save(question));
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        Question question = questionService.findById(id).orElseThrow(NotFoundException::new);
        Poll poll = pollService.findByQuestion(question).orElseThrow(NotFoundException::new);
        if (!poll.removeQuestion(question)) {
            throw new NotFoundException();
        }
        pollService.save(poll);
    }
}
