package org.psu.lab5.service;

import java.util.Collections;

import javax.validation.Valid;

import org.psu.lab5.authentication.JwtAuthentication;
import org.psu.lab5.exception.UserExistsException;
import org.psu.lab5.exception.UserNotFoundException;
import org.psu.lab5.exception.WrongPasswordException;
import org.psu.lab5.model.User;
import org.psu.lab5.pojo.LoginRequest;
import org.psu.lab5.pojo.LoginResponse;
import org.psu.lab5.pojo.RegisterRequest;
import org.psu.lab5.pojo.RegisterResponse;
import org.psu.lab5.repository.UserRepository;
import org.psu.lab5.utils.JwtUtils;
import org.psu.lab5.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public LoginResponse login(@NonNull @Valid LoginRequest request)
            throws UserNotFoundException, WrongPasswordException {
        User user = userRepository.getByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException());
        user.setLoginCount(user.getLoginCount() + 1);
        userRepository.save(user);
        if (user.getPassword().equals(request.getPassword())) {
            final String token = jwtUtils.generate(user);
            return new LoginResponse(token);
        } else {
            throw new WrongPasswordException();
        }
    }

    public RegisterResponse register(@NonNull @Valid RegisterRequest request) throws UserExistsException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserExistsException();
        }
        final User newUser = new User(null,
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                Collections.singleton(Role.ADMIN),
                null,
                0);
        userRepository.save(newUser);
        return new RegisterResponse(true, "");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
