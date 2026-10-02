package br.com.yggdrasil.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8150982916675560184L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("User with ID " + id + " not found.");
    }
}