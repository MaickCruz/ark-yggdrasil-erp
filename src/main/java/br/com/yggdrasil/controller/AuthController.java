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
import br.com.yggdrasil.security.CustomUserDetails;
import br.com.yggdrasil.service.AuthService;
import br.com.yggdrasil.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
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

    @PostMapping("/set-password")
    public ResponseEntity<Void> setPassword(
            @Valid @RequestBody SetPasswordRequestDTO dto) {

        userService.setPassword(dto.getToken(), dto.getNewPassword());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponseDTO> me(Authentication authentication) {
    	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    	User user = userDetails.getUser();
    	
    	CurrentUserResponseDTO response = new CurrentUserResponseDTO(user);
    	return ResponseEntity.ok(response);
    }
}