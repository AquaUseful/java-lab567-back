package org.psu.lab5.pojo;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponse {
    private final String tokenType = "Bearer";
    private String token;
}
