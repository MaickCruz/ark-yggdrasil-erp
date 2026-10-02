package br.com.yggdrasil.exception;

public class EmailAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1749258166053402332L;

	public EmailAlreadyExistsException(String email) {
        super("Email '" + email + "' is already registered.");
    }
}