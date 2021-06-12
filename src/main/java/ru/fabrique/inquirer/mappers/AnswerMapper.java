package ru.fabrique.inquirer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import ru.fabrique.inquirer.dto.AnswerDto;
import ru.fabrique.inquirer.model.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerDto toDto(Answer answer);
    List<AnswerDto> toDtos(List<Answer> answers);
}
