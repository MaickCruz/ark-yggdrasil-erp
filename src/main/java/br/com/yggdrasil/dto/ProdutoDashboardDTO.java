package br.com.yggdrasil.dto;

import java.math.BigDecimal;

public class ProdutoDashboardDTO {

    private Long produtoId;

    private String produtoNome;

    private Long totalVendido;

    private BigDecimal valorTotal;

    private BigDecimal lucroBruto;

    public ProdutoDashboardDTO(
            Long produtoId,
            String produtoNome,
            Long totalVendido,
            BigDecimal valorTotal,
            BigDecimal lucroBruto) {

        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.totalVendido = totalVendido;
        this.valorTotal = valorTotal;
        this.lucroBruto = lucroBruto;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public Long getTotalVendido() {
        return totalVendido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getLucroBruto() {
        return lucroBruto;
    }
}