package org.psu.lab5.repository;

import java.util.Optional;

import org.psu.lab5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByUsername(String username);

    Boolean existsByUsername(String username);
}
