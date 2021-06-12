package ru.fabrique.inquirer.services;

import java.util.Optional;
import ru.fabrique.inquirer.model.User;

public interface UserService {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUserAnonymousId(Long anonymousId);
    Optional<User> findByUsername(String username);
}
