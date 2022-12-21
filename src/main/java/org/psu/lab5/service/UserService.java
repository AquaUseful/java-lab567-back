package org.psu.lab5.service;

import org.apache.catalina.connector.Request;
import org.psu.lab5.authentication.JwtAuthentication;
import org.psu.lab5.exception.ResourceNotFoundException;
import org.psu.lab5.exception.UserExistsException;
import org.psu.lab5.model.User;
import org.psu.lab5.pojo.RegisterRequest;
import org.psu.lab5.pojo.NewUserRequest;
import org.psu.lab5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BinfileService binfileService;
    @Autowired
    AuthService authService;

    public User getByUsername(String username) {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public JwtAuthentication authInfo() {
        return authService.getAuthInfo();
    }

    public boolean hasFile(String username) {
        return this.getByUsername(username).getFile() != null;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void applyPatchByUsername(String username, NewUserRequest patch) {
        User user = this.getByUsername(username);
        if (patch.getUsername() != null) {
            user.setUsername(patch.getUsername());
        }
        if (patch.getEmail() != null) {
            user.setEmail(patch.getEmail());
        }
        if (patch.getPassword() != null) {
            user.setPassword(patch.getPassword());
        }
        if (patch.getRole() != null) {
            user.setRoles(Collections.singleton(patch.getRole()));
        }
        this.save(user);
    }

    public void addUser(@NotNull @Valid NewUserRequest request) throws UserExistsException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserExistsException();
        }
        final User newUser = new User(
                null,
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                Collections.singleton(request.getRole()),
                null,
                0,
                null);
        this.save(newUser);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

}
