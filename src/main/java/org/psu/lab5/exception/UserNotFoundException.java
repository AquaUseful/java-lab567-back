package org.psu.lab5.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends LoginException {
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
