package br.com.yggdrasil.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.yggdrasil.dto.SetPasswordRequestDTO;
import br.com.yggdrasil.dto.LoginRequestDTO;
import br.com.yggdrasil.dto.CurrentUserResponseDTO;
import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.security.UsuarioDetails;
import br.com.yggdrasil.service.AuthService;
import br.com.yggdrasil.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService usuarioService;

    public AuthController(AuthService authService, UserService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO dto) {

        String token = authService.login(dto);

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false) // true quando estiver usando HTTPS
                .sameSite("Strict")
                .path("/")
                .maxAge(60 * 60 * 10) // 10 horas
                .build();

        return ResponseEntity.ok()
        		.header("Set-Cookie", cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header("Set-Cookie", cookie.toString())
                .build();
    }

    @PostMapping("/definir-senha")
    public ResponseEntity<Void> definirSenha(
            @Valid @RequestBody SetPasswordRequestDTO dto) {

        usuarioService.setPassword(dto.getToken(), dto.getNewPassword());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> me(Authentication authentication) {
    	UsuarioDetails userDetails = (UsuarioDetails) authentication.getPrincipal();
    	User usuario = userDetails.getUsuario();
    	
    	CurrentUserResponseDTO response = new CurrentUserResponseDTO(usuario);
    	return ResponseEntity.ok(response);
    }
}