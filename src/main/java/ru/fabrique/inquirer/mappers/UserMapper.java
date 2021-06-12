package ru.fabrique.inquirer.mappers;

import org.mapstruct.Mapper;
import ru.fabrique.inquirer.dto.UserDto;
import ru.fabrique.inquirer.model.User;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserDto toDto(User user);
}
