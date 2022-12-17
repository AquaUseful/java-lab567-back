package org.psu.lab5.exception;

public class WrongPasswordException extends LoginException {
    public WrongPasswordException() {
        super("Неверный пароль");
    }
}
