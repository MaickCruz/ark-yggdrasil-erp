package br.com.yggdrasil.dto;

import java.math.BigDecimal;

public class VendedorDashboardDTO {

	private Long vendedorId;
	private String vendedorNome;
	private Long quantidadeVendas;
	private BigDecimal faturamento;

	public VendedorDashboardDTO(Long vendedorId, String vendedorNome, Long quantidadeVendas, BigDecimal faturamento) {

		this.vendedorId = vendedorId;
		this.vendedorNome = vendedorNome;
		this.quantidadeVendas = quantidadeVendas;
		this.faturamento = faturamento;
	}

	public Long getVendedorId() {
		return vendedorId;
	}

	public String getVendedorNome() {
		return vendedorNome;
	}

	public Long getQuantidadeVendas() {
		return quantidadeVendas;
	}

	public BigDecimal getFaturamento() {
		return faturamento;
	}

}
