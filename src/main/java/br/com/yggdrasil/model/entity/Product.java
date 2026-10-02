package br.com.yggdrasil.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description", length = 255)
	private String description;
	
	@NotBlank(message = "Name is required.")
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@DecimalMin(value = "0.0", inclusive = false, message = "Cost price must be greater than zero.")
	@Column(name = "cost_price", precision = 10, scale = 2, nullable = false)
	private BigDecimal costPrice;

	@DecimalMin(value = "0.0", inclusive = false, message = "Sale price must be greater than zero.")
	@Column(name = "sale_price", precision = 10, scale = 2, nullable = false)
	private BigDecimal salePrice;
	
	@PositiveOrZero(message = "Stock quantity cannot be negative.")
	@Column(name = "stock_quantity", nullable = false)
	private Integer stockQuantity = 0;
	
	public Product() {
	}

	public Product(String name, String description, BigDecimal costPrice, BigDecimal salePrice, Integer stockQuantity) {
		this.name = name;
		this.description = description;
		this.costPrice = costPrice;
		this.salePrice = salePrice;
		this.stockQuantity = stockQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	
}
