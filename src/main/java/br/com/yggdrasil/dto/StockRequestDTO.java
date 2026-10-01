package br.com.yggdrasil.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class StockRequestDTO {

    @NotNull(message = "Quantity is required.")
    @PositiveOrZero(message = "Quantity cannot be negative.")
    private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}
    
    
    
}
