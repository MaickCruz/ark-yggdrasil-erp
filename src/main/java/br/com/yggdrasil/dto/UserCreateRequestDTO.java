package br.com.yggdrasil.dto;

import br.com.yggdrasil.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCreateRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Role is required")
    private UserRole role;
    
    public UserCreateRequestDTO() {
	}
    
	public UserCreateRequestDTO(@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Email is required") @Email(message = "Please enter a valid email address") String email,
			@NotNull(message = "Role is required") UserRole role) {
		super();
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	

    
    
}