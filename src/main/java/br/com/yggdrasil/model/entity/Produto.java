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
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descricao", length = 255)
	private String descricao;
	
	@NotBlank(message = "Nome inválido")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@DecimalMin(value = "0.0", inclusive = false, message = "O preço de custo deve ser maior que zero")
	@Column(name = "preco_custo", precision = 10, scale = 2, nullable = false)
	private BigDecimal precoCusto;

	@DecimalMin(value = "0.0", inclusive = false, message = "O preço de venda deve ser maior que zero")
	@Column(name = "preco_venda", precision = 10, scale = 2, nullable = false)
	private BigDecimal precoVenda;
	
	@PositiveOrZero(message = "A quantidade em estoque não pode ser negativa")
	@Column(name = "quantidade_estoque", nullable = false)
	private Integer quantidadeEstoque = 0;
	
	public Produto() {
	}

	public Produto(String nome, String descricao, BigDecimal precoCusto, BigDecimal precoVenda, Integer quantidadeEstoque) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	
}
