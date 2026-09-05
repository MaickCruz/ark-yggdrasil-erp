package br.com.yggdrasil.model.entity;

import br.com.yggdrasil.model.enums.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome inválido")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@NotBlank(message = "A senha é obrigatória")
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "Por favor, insira um e-mail com formato válido")
	@Column(name = "email", length = 150, unique = true, nullable = false)
	private String email;
	
	@NotNull(message = "O tipo de usuário é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_usuario", length = 20, nullable = false)
	private TipoUsuario tipo;
	
	public Usuario() {
	}

	public Usuario(String nome, String senha, String email, TipoUsuario tipo) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.tipo = tipo;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	
	
	
}
