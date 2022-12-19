package org.psu.lab5.utils;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.psu.lab5.model.News;
import org.psu.lab5.model.Role;
import org.psu.lab5.model.User;
import org.psu.lab5.model.Comment;
import org.psu.lab5.repository.CommentRepository;
import org.psu.lab5.repository.NewsRepository;
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

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {

            User testUser = new User(null,
                    "user",
                    "user",
                    "user@email.com",
                    Collections.singleton(Role.USER),
                    null,
                    0, null);
            userRepository.save(testUser);

            userRepository.save(new User(null,
                    "admin",
                    "admin",
                    "admin@email.com",
                    Collections.singleton(Role.ADMIN),
                    null,
                    0, null));

            News testNews = new News(
                    null,
                    "Тестовое название",
                    "Тестовое содержание",
                    0L,
                    null, null);
            Comment testComment = new Comment(null, "Тестовый комментарий", testUser, testNews);

            newsRepository.save(testNews);
            commentRepository.save(testComment);

        } catch (DataIntegrityViolationException e) {

        }
    }

}
