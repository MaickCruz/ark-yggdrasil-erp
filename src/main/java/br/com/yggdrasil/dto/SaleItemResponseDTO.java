package br.com.yggdrasil.dto;

import java.math.BigDecimal;

import br.com.yggdrasil.model.entity.SaleItem;

public class SaleItemResponseDTO {

    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public SaleItemResponseDTO() {
    }

    public SaleItemResponseDTO(SaleItem saleItem) {
        this.productName = saleItem.getProduct().getName();
        this.quantity = saleItem.getQuantity();
        this.unitPrice = saleItem.getUnitPrice();
        this.subtotal = saleItem.getUnitPrice().multiply(BigDecimal.valueOf(saleItem.getQuantity()));
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}