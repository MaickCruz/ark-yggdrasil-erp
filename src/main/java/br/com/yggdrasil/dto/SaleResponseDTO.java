package br.com.yggdrasil.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.yggdrasil.model.entity.Sale;

public class SaleResponseDTO {

    private Long id;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private String salespersonName;
    private List<SaleItemResponseDTO> items;

    public SaleResponseDTO() {
    }

    public SaleResponseDTO(Sale sale) {
        this.id = sale.getId();
        this.saleDate = sale.getSaleDate();
        this.totalAmount = sale.getTotalAmount();
        this.salespersonName = sale.getSalesperson().getName();
        this.items = sale.getItems().stream()
                .map(SaleItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSalespersonName() {
        return salespersonName;
    }

    public void setSalespersonName(String salespersonName) {
        this.salespersonName = salespersonName;
    }

    public List<SaleItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<SaleItemResponseDTO> items) {
        this.items = items;
    }
}