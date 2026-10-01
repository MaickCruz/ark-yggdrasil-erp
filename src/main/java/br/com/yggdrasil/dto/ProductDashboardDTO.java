package br.com.yggdrasil.dto;

import java.math.BigDecimal;

public class ProductDashboardDTO {

    private Long productId;

    private String productName;

    private Long totalSold;

    private BigDecimal totalRevenue;

    private BigDecimal grossProfit;

    public ProductDashboardDTO(
            Long productId,
            String productName,
            Long totalSold,
            BigDecimal totalRevenue,
            BigDecimal grossProfit) {

        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
        this.totalRevenue = totalRevenue;
        this.grossProfit = grossProfit;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getTotalSold() {
        return totalSold;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }
}