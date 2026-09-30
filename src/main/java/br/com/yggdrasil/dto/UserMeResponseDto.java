package br.com.yggdrasil.dto;

import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.model.enums.TipoUsuario;

public class UserMeResponseDto {

	private Long id;
	
	private String nome;
	
	private String email;
	
	private TipoUsuario tipo;
	
	public UserMeResponseDto() {
	}

	public UserMeResponseDto(Long id, String nome, String email, TipoUsuario tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipo = tipo;
	}
	
	public UserMeResponseDto(Usuario usuario) {
	    this.id = usuario.getId();
	    this.nome = usuario.getNome();
	    this.email = usuario.getEmail();
	    this.tipo = usuario.getTipo();
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
