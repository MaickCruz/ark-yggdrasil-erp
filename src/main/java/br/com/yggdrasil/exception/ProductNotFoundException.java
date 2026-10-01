package br.com.yggdrasil.exception;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8150982916675560184L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Long id) {
		super("Product with ID " + id + " not found");
	}
}
