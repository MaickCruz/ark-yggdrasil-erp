package br.com.yggdrasil.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9212104584596582236L;
	
    protected NotFoundException(String message) {
        super(message);
    }

}
