package br.com.yggdrasil.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.yggdrasil.model.entity.Venda;

public class VendaResponseDTO {

    private Long id;
    private LocalDateTime data;
    private BigDecimal valorTotal;
    private String vendedorNome;
    private List<ItemVendaResponseDTO> itens;

    public VendaResponseDTO() {
    }

    public VendaResponseDTO(Venda venda) {
        this.id = venda.getId();
        this.data = venda.getData();
        this.valorTotal = venda.getValorTotal();
        this.vendedorNome = venda.getVendedor().getName();
        this.itens = venda.getItens().stream()
                .map(ItemVendaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getVendedorNome() {
        return vendedorNome;
    }

    public void setVendedorNome(String vendedorNome) {
        this.vendedorNome = vendedorNome;
    }

    public List<ItemVendaResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaResponseDTO> itens) {
        this.itens = itens;
    }
}