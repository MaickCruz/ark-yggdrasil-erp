package br.com.yggdrasil.exception;

@SuppressWarnings("serial")
public class TokenInvalidoOuExpiradoException extends RuntimeException {
	
    public TokenInvalidoOuExpiradoException() {
        super("Token inválido ou expirado");
    }
}