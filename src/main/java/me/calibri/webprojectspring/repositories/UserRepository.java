package me.calibri.webprojectspring.repositories;

import me.calibri.webprojectspring.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(final String username, final String email);
    Optional<User> findByUserId(final Long id);
}
