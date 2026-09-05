package br.com.yggdrasil.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8150982916675560184L;

	public UsuarioNaoEncontradoException(String message) {
		super(message);
	}

	public UsuarioNaoEncontradoException(Long id) {
		super("Usuário com id " + id + " não encontrado");
	}
}
