package ru.fabrique.inquirer.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fabrique.inquirer.model.Poll;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollWithUserAnswerDto {
    private Long id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private String description;
    private Map<QuestionDto, List<AnswerDto>> questions;

    public static PollWithUserAnswerDto toDto(Poll poll) {
        PollWithUserAnswerDto result = new PollWithUserAnswerDto();
        result.setId(poll.getId());
        result.setName(poll.getName());
        result.setDateStart(poll.getDateStart());
        result.setDateEnd(poll.getDateEnd());
        result.setDescription(poll.getDescription());
        return result;
    }
}
