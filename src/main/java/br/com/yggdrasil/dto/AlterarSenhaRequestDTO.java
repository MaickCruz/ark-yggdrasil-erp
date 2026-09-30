package br.com.yggdrasil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlterarSenhaRequestDTO {

	
    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
	private String novaSenha;
    private String antigaSenha;
	
    public AlterarSenhaRequestDTO() {
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

	public String getAntigaSenha() {
		return antigaSenha;
	}

	public void setAntigaSenha(String antigaSenha) {
		this.antigaSenha = antigaSenha;
	}
    
    
    
}
