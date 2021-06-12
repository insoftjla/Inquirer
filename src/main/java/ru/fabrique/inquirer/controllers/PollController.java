package ru.fabrique.inquirer.controllers;

import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fabrique.inquirer.dto.PollDto;
import ru.fabrique.inquirer.dto.PollWithUserAnswerDto;
import ru.fabrique.inquirer.dto.QuestionDto;
import ru.fabrique.inquirer.dto.UserAnswerDto;
import ru.fabrique.inquirer.exceptions.UnauthorizedException;
import ru.fabrique.inquirer.logics.PollLogic;
import ru.fabrique.inquirer.mappers.PollMapper;
import ru.fabrique.inquirer.mappers.QuestionMapper;
import ru.fabrique.inquirer.model.User;

@RequiredArgsConstructor
@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollLogic pollLogic;
    private final PollMapper pollMapper;
    private final QuestionMapper questionMapper;

    @ApiOperation("Get active polls with pagination")
    @GetMapping
    public List<PollDto> getAllActiveWithPagination(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "name") String sort
    ) {
        return pollMapper.toDtos(pollLogic.getActivePollsWithPagination(page, size, sort));
    }

    @ApiOperation(value = "Get active poll by ID")
    @GetMapping("{id}")
    public PollDto getActiveById(@PathVariable Long id) {
        return pollMapper.toDto(pollLogic.getActivePollById(id));
    }

    @ApiOperation(
            value = "Get current question for poll",
            notes = "Если активных вопросов не найдено, response status 404")
    @GetMapping("{id}/pass")
    public QuestionDto getCurrentActiveQuestionForPoll(
            @PathVariable Long id,
            @RequestParam(required = false) Long anonymousId,
            Principal principal
    ) {
        if (anonymousId == null) {
            if (principal != null) {
                return questionMapper.toDto(pollLogic.getCurrentActiveQuestionByUsernameAndPollId(principal.getName(), id));
            } else {
                throw new UnauthorizedException();
            }
        }
        return questionMapper.toDto(pollLogic.getNextCurrentQuestionByUserAnonymousIdAndPollId(anonymousId, id));
    }

    @ApiOperation(
            value = "Answer the current question and get the next survey question",
            notes = "Если активных вопросов не найдено - Response status 404.\n" +
                    "Если пользователь уже ответил на вопрос по указанному идентификатору - Response status 208.\n\n" +
                    "В зависимости от типа вопроса учитываются соответствующие поля(поле questionId обязательное):\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;CHECKBOX - множественный выбор. В массиве answerIds учитываются все указанные ID ответов имеющиеся в вопросе, поле text игнорируется;\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;RADIO - одиночный выбор. В массиве answerIds учитываются только первый ID, остальные игнорируются, поле text игнорируется;\n" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;TEXT - ответ текстом, поле answerIds игнорируется.")
    @PostMapping("{id}/pass")
    public QuestionDto answerAndGetNextActiveQuestionForPoll(
            @PathVariable Long id,
            @RequestParam(required = false) Long anonymousId,
            @RequestBody UserAnswerDto userAnswerDto,
            Principal principal
    ) {
        User user;
        if (anonymousId == null) {
            if (principal != null) {
                user = pollLogic.getUserByUsername(principal.getName());
            } else {
                throw new UnauthorizedException();
            }
        } else {
            user = pollLogic.saveAnonymousUserIfNotPresent(anonymousId);
        }
        pollLogic.saveUserAnswer(id, user, userAnswerDto);
        return questionMapper.toDto(pollLogic.getNextActiveQuestionByUserIdAndPollId(user.getId(), id));
    }

    @ApiOperation(value = "Get completed polls with details bu anonymous ID")
    @GetMapping("/user")
    public List<PollWithUserAnswerDto> getPollsPassedByAnonymousId(@RequestParam Long anonymousId) {
        return pollLogic.getPollsPassedByAnonymousId(anonymousId);
    }
}
