package br.com.yggdrasil.dto;

import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.model.enums.TipoUsuario;

public class UsuarioResponseDTO {

	private Long id;
	private String nome;
	private String email;
	private TipoUsuario tipo;

	public UsuarioResponseDTO() {
	}

	public UsuarioResponseDTO(Usuario usuario) {
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