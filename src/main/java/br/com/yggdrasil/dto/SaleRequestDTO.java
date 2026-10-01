package br.com.yggdrasil.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SaleRequestDTO {

    @NotNull(message = "Salesperson is required.")
    private Long salespersonId;

    @NotEmpty(message = "Sale must contain at least one item.")
    @Valid
    private List<SaleItemRequestDTO> items;

    public SaleRequestDTO() {
    }

    public Long getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(Long salespersonId) {
        this.salespersonId = salespersonId;
    }

    public List<SaleItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<SaleItemRequestDTO> items) {
        this.items = items;
    }
}