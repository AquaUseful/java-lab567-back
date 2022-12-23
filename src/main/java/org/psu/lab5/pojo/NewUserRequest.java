package org.psu.lab5.pojo;

import javax.validation.constraints.NotBlank;

import org.psu.lab5.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String username;
    @NotBlank(message = "Пустой email!")
    private String email;
    @NotBlank(message = "Пустой пароль!")
    private String password;
    private Role role;
}
