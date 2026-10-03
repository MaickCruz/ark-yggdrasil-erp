package br.com.yggdrasil.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.UserCreateRequestDTO;
import br.com.yggdrasil.dto.UserResponseDTO;
import br.com.yggdrasil.dto.UserUpdateRequestDTO;
import br.com.yggdrasil.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateRequestDTO dto) {

	    UserResponseDTO response = userService.createUser(dto);
	    
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
		UserResponseDTO user = new UserResponseDTO(userService.getUserById(id));
		return ResponseEntity.ok(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUserById(
	        @PathVariable Long id,
	        @Valid @RequestBody UserUpdateRequestDTO dto) {

	    UserResponseDTO response =
	            userService.updateUserById(id, dto);

	    return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/password-reset")
	public ResponseEntity<Void> requestPasswordReset(
	        @PathVariable Long id) {

	    userService.requestPasswordReset(id);

	    return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}

}
