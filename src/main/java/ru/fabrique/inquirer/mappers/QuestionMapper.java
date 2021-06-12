package ru.fabrique.inquirer.mappers;

import org.mapstruct.Mapper;
import ru.fabrique.inquirer.dto.QuestionDto;
import ru.fabrique.inquirer.model.Question;

@Mapper(componentModel="spring")
public interface QuestionMapper {

    QuestionDto toDto(Question question);
}
