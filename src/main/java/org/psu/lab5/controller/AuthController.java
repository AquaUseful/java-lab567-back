package org.psu.lab5.controller;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.psu.lab5.exception.UserExistsException;
import org.psu.lab5.exception.UserNotFoundException;
import org.psu.lab5.exception.WrongPasswordException;
import org.psu.lab5.pojo.LoginRequest;
import org.psu.lab5.pojo.LoginResponse;
import org.psu.lab5.pojo.RegisterRequest;
import org.psu.lab5.pojo.RegisterResponse;
import org.psu.lab5.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request)
            throws UserNotFoundException, WrongPasswordException {
        final LoginResponse resp = authService.login(request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request)
            throws UserExistsException {
        final RegisterResponse resp = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse(true, ""));
    }

}
