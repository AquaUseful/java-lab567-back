package org.psu.lab5.service;

import org.psu.lab5.authentication.JwtAuthentication;
import org.psu.lab5.model.User;
import org.psu.lab5.pojo.RegisterRequest;
import org.psu.lab5.pojo.UserPatch;
import org.psu.lab5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
                .orElseThrow(() -> new RuntimeException("User not found"));
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

    public void applyPatchByUsername(String username, UserPatch patch) {
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
        if (patch.getRoles() != null) {
            user.setRoles(patch.getRoles());
        }
        this.save(user);
    }

}
