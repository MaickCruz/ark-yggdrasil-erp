package br.com.yggdrasil.exception;

public class CredenciaisInvalidasException extends RuntimeException {

	private static final long serialVersionUID = -2619810519269451496L;

	public CredenciaisInvalidasException() {
		super("E-mail ou senha inválidos");
	}
	
}
