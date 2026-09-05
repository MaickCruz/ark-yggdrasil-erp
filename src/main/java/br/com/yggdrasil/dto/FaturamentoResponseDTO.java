package br.com.yggdrasil.dto;

import java.math.BigDecimal;

public class FaturamentoResponseDTO {

    private BigDecimal valorTotal;
    private Long quantidadeVendas;

    public FaturamentoResponseDTO(BigDecimal valorTotal, Long quantidadeVendas) {
        this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
        this.quantidadeVendas = quantidadeVendas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Long getQuantidadeVendas() {
        return quantidadeVendas;
    }
}