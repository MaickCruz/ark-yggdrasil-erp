package br.com.yggdrasil.dto;

import java.math.BigDecimal;

import br.com.yggdrasil.model.entity.ItemVenda;

public class ItemVendaResponseDTO {

    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    public ItemVendaResponseDTO() {
    }

    public ItemVendaResponseDTO(ItemVenda itemVenda) {
        this.produtoNome = itemVenda.getProduto().getName();
        this.quantidade = itemVenda.getQuantidade();
        this.precoUnitario = itemVenda.getPrecoUnitario();
        this.subtotal = itemVenda.getPrecoUnitario().multiply(BigDecimal.valueOf(itemVenda.getQuantidade()));
    }

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}