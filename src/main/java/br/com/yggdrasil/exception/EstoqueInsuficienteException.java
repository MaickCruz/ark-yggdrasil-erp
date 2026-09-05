package br.com.yggdrasil.exception;

public class EstoqueInsuficienteException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6946328831167646908L;

	public EstoqueInsuficienteException(String nomeProduto, Integer disponivel, Integer solicitado) {
        super("Estoque insuficiente para o produto '" + nomeProduto + "'. Disponível: "
                + disponivel + ", solicitado: " + solicitado);
    }
}