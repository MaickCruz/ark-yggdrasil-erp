package br.com.yggdrasil.exception;

public class InvalidOrExpiredTokenException extends RuntimeException {

	private static final long serialVersionUID = -1681505715259218346L;

	public InvalidOrExpiredTokenException() {
        super("Token is invalid or expired.");
    }
}