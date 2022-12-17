package org.psu.lab5.utils;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.psu.lab5.model.Role;
import org.psu.lab5.model.User;
import org.psu.lab5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class DemoData implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            userRepository.save(
                    new User(null,
                            "admin",
                            "admin",
                            "admin@email.com",
                            Stream.of(Role.ADMIN, Role.USER).collect(Collectors.toSet()),
                            null,
                            0));
            userRepository.save(
                    new User(null,
                            "user",
                            "user",
                            "user@email.com",
                            Collections.singleton(Role.USER),
                            null,
                            0));
        } catch (DataIntegrityViolationException e) {

        }
    }

}
