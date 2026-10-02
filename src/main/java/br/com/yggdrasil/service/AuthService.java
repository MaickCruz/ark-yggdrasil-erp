package br.com.yggdrasil.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.LoginRequestDTO;
import br.com.yggdrasil.exception.InvalidCredentialsException;
import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.repository.UserRepository;
import br.com.yggdrasil.security.JwtService;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
	
	public String login(LoginRequestDTO dto) {
		
		User user = userRepository.findByEmail(dto.getEmail())
			.orElseThrow(() -> new InvalidCredentialsException());
		
	    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
	        throw new InvalidCredentialsException();
	    }

	    return jwtService.generateToken(user.getEmail());
	}
	
	
	
}
