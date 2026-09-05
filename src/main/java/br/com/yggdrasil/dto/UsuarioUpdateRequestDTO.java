package br.com.yggdrasil.dto;

import br.com.yggdrasil.model.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioUpdateRequestDTO {

	@NotBlank(message = "Nome inválido")
	private String nome;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "Por favor, insira um e-mail com formato válido")
	private String email;

	@NotNull(message = "O tipo de usuário é obrigatório")
	private TipoUsuario tipo;

	public UsuarioUpdateRequestDTO() {
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
