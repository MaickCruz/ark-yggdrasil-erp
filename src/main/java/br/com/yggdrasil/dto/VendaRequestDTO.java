package br.com.yggdrasil.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VendaRequestDTO {

    @NotNull(message = "O vendedor é obrigatório")
    private Long vendedorId;

    @NotEmpty(message = "A venda precisa ter pelo menos um item")
    @Valid
    private List<ItemVendaRequestDTO> itens;

    public VendaRequestDTO() {
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }

    public List<ItemVendaRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequestDTO> itens) {
        this.itens = itens;
    }
}