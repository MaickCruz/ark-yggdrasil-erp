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
@Table(name = "venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data", nullable = false)
	private LocalDateTime data = LocalDateTime.now();

	@DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero.")
	@Column(name = "valor_total", precision = 10, scale = 2, nullable = false)
	private BigDecimal valorTotal;

	@NotNull(message = "A venda precisa de um vendedor.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendedor_id", nullable = false)
	private Usuario vendedor;

	public Venda() {
	}

	public Venda(BigDecimal valorTotal, Usuario vendedor) {
		super();
		this.valorTotal = valorTotal;
		this.vendedor = vendedor;
	}

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVenda> itens = new ArrayList<>();

	public List<ItemVenda> getItens() {
		return itens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

}
