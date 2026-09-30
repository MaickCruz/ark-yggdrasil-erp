package br.com.yggdrasil.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.LoginRequestDTO;
import br.com.yggdrasil.exception.CredenciaisInvalidasException;
import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.repository.UsuarioRepository;
import br.com.yggdrasil.security.JwtService;

@Service
public class AuthService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		super();
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	public String login(LoginRequestDTO dto) {
		
		Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
			.orElseThrow(() -> new CredenciaisInvalidasException());
		
	    if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
	        throw new CredenciaisInvalidasException();
	    }

	    return jwtService.gerarToken(usuario.getEmail());
	}
	
	
	
}
