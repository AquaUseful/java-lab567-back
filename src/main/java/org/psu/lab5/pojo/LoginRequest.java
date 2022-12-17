package org.psu.lab5.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Пустой логин!")
    private String username;
    @NotBlank(message = "Пустой пароль!")
    private String password;
}
