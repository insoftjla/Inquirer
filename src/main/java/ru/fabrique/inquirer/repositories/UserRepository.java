package ru.fabrique.inquirer.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fabrique.inquirer.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByAnonymousId(Long anonymousId);
    Optional<User> findByUsername(String username);
}
