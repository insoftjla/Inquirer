package ru.fabrique.inquirer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fabrique.inquirer.dto.UserDto;
import ru.fabrique.inquirer.exceptions.NotFoundException;
import ru.fabrique.inquirer.mappers.UserMapper;
import ru.fabrique.inquirer.model.User;
import ru.fabrique.inquirer.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/login")
    public UserDto login() {
        User user = userService.findById(1L).orElseThrow(NotFoundException::new);
        return userMapper.toDto(user);
    }
}
