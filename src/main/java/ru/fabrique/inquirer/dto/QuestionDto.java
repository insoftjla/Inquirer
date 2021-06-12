package ru.fabrique.inquirer.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.fabrique.inquirer.model.Poll;
import ru.fabrique.inquirer.model.QuestionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QuestionDto {
    private Long id;
    private String text;
    private QuestionType type;
    private List<AnswerDto> answers;

    @Override
    public String toString() {
        return text;
    }
}
