package br.com.yggdrasil.exception;

public class SaleNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -5598125177183817266L;

    public SaleNotFoundException(String message) {
        super(message);
    }

    public SaleNotFoundException(Long id) {
        super("Sale with ID " + id + " not found.");
    }
}