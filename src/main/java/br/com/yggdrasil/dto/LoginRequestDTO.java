package br.com.yggdrasil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

	@NotBlank(message = "O email é obrigatória")
	@Email(message = "Por favor, insira um e-mail com formato válido")
	private String email;
	
	@NotBlank(message = "A senha é obrigatória")
	private String senha;

	public LoginRequestDTO() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
	
}
