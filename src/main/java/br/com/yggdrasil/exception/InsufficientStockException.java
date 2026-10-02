package br.com.yggdrasil.exception;

public class InsufficientStockException extends RuntimeException {

    private static final long serialVersionUID = 6946328831167646908L;

    public InsufficientStockException(
            String productName,
            Integer available,
            Integer requested) {

        super(
                "Insufficient stock for product '" + productName
                + "'. Available: " + available
                + ", requested: " + requested + "."
        );
    }
}