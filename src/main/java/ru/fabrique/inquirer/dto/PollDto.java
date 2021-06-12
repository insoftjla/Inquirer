package ru.fabrique.inquirer.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PollDto {
    private Long id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private String description;
    private List<QuestionDto> questions;
}
