package br.com.yggdrasil.dto;

import java.math.BigDecimal;

public class SalespersonDashboardDTO {

	private Long salespersonId;
	private String salespersonName;
	private Long salesCount;
	private BigDecimal revenue;

	public SalespersonDashboardDTO(Long salespersonId, String salespersonName, Long salesCount, BigDecimal revenue) {

		this.salespersonId = salespersonId;
		this.salespersonName = salespersonName;
		this.salesCount = salesCount;
		this.revenue = revenue;
	}

	public Long getSalespersonId() {
		return salespersonId;
	}

	public String getSalespersonName() {
		return salespersonName;
	}

	public Long getSalesCount() {
		return salesCount;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

}
