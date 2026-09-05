package br.com.yggdrasil.exception;

public class VendaNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5598125177183817266L;

	public VendaNaoEncontradaException(String message) {
		super(message);
	}

	public VendaNaoEncontradaException(Long id) {
		super("Venda com id " + id + " não encontrado");
	}
}
