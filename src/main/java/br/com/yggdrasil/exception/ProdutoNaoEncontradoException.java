package br.com.yggdrasil.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 8150982916675560184L;

	public ProdutoNaoEncontradoException(String message) {
		super(message);
	}

	public ProdutoNaoEncontradoException(Long id) {
		super("Produto com id " + id + " não encontrado");
	}
}
