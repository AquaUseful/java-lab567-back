package org.psu.lab5.pojo;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

import org.psu.lab5.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    @NotBlank(message = "Пустое имя!")
    private String username;
    @NotBlank(message = "Пустой email!")
    private String email;
    @NotBlank(message = "Пустой пароль!")
    private String password;
    private Role role;
}
