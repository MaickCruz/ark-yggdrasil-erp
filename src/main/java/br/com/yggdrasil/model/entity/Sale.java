package br.com.yggdrasil.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sale_date", nullable = false)
	private LocalDateTime saleDate = LocalDateTime.now();

	@DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than zero.")
	@Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
	private BigDecimal totalAmount;

	@NotNull(message = "Salesperson is required.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salesperson_id", nullable = false)
	private User salesperson;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleItem> items = new ArrayList<>();

	public Sale() {
	}
	
	public Sale(BigDecimal totalAmount, User salesperson) {
		super();
		this.totalAmount = totalAmount;
		this.salesperson = salesperson;
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

	public User getSalesperson() {
		return salesperson;
	}

	public void setSalesperson(User salesperson) {
		this.salesperson = salesperson;
	}

	public List<SaleItem> getItems() {
		return items;
	}
}
