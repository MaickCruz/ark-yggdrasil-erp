package br.com.yggdrasil.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class EstoqueRequestDTO {

    @NotNull(message = "A quantidade é obrigatória")
    @PositiveOrZero(message = "A quantidade não pode ser negativa")
    private Integer quantidade;

	public Integer getQuantidade() {
		return quantidade;
	}
    
    
    
}
