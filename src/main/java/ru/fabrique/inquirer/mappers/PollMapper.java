package ru.fabrique.inquirer.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import ru.fabrique.inquirer.dto.PollDto;
import ru.fabrique.inquirer.model.Poll;

@Mapper(componentModel="spring")
public interface PollMapper {

    PollDto toDto(Poll poll);
    List<PollDto> toDtos(List<Poll> polls);
}
