package ru.fabrique.inquirer.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDto {
    private Long questionId;
    private List<Long> answerIds;
    private String text;
}
