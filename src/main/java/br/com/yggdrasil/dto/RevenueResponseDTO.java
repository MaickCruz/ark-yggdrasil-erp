package br.com.yggdrasil.dto;

import java.math.BigDecimal;

public class RevenueResponseDTO {

    private BigDecimal totalRevenue;
    private Long salesCount;

    public RevenueResponseDTO(BigDecimal totalRevenue, Long salesCount) {
        this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
        this.salesCount = salesCount;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public Long getSalesCount() {
        return salesCount;
    }
}