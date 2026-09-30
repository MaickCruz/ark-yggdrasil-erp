package br.com.yggdrasil.exception;

public class InvalidCredentialsException extends RuntimeException {

    private static final long serialVersionUID = -2619810519269451496L;

    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }
}