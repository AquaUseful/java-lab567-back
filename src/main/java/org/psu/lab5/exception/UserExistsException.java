package org.psu.lab5.exception;

public class UserExistsException extends RegisterException {
    public UserExistsException() {
        super("Пользователь уже существует");
    }
}
