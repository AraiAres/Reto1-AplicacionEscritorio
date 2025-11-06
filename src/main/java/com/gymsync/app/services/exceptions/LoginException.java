package com.gymsync.app.services.exceptions;

public class LoginException extends Exception {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Credenciales inválidas!";

    public LoginException() {
        super(DEFAULT_MESSAGE);
    }

    public LoginException(String message) {
        super(message);
    }

}
