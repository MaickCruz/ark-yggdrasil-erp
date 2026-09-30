package br.com.yggdrasil.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.UsuarioResponseDTO;
import br.com.yggdrasil.dto.UsuarioUpdateRequestDTO;
import br.com.yggdrasil.exception.CredenciaisInvalidasException;
import br.com.yggdrasil.exception.EmailCadastradoException;
import br.com.yggdrasil.exception.TokenInvalidoOuExpiradoException;
import br.com.yggdrasil.exception.UsuarioNaoEncontradoException;
import br.com.yggdrasil.model.entity.TokenDefinicaoSenha;
import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.repository.TokenDefinicaoSenhaRepository;
import br.com.yggdrasil.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final TokenDefinicaoSenhaRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenDefinicaoSenhaRepository tokenRepository, EmailService emailService) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
	}

	public Usuario cadastrarUsuario(Usuario usuario) {
	    if (usuarioRepository.existsByEmail(usuario.getEmail())) {
	        throw new EmailCadastradoException(usuario.getEmail());
	    }

	    String senhaTemporaria = UUID.randomUUID().toString();
	    usuario.setSenha(passwordEncoder.encode(senhaTemporaria));
	    Usuario usuarioSalvo = usuarioRepository.save(usuario);

	    gerarTokenDefinicaoSenha(usuarioSalvo);

	    return usuarioSalvo;
	}
	
	private void gerarTokenDefinicaoSenha(Usuario usuario) {
	    String token = UUID.randomUUID().toString();

	    TokenDefinicaoSenha tokenEntity = new TokenDefinicaoSenha();
	    tokenEntity.setToken(token);
	    tokenEntity.setUsuario(usuario);
	    tokenEntity.setDataExpiracao(LocalDateTime.now().plusHours(24));
	    tokenRepository.save(tokenEntity);

	    String link = "http://localhost:8080/auth/definir-senha?token=" + token;
	    emailService.enviarEmailDefinicaoSenha(usuario.getEmail(), usuario.getNome(), link);
	}
	
	public void definirSenha(String token, String novaSenha) {
	    TokenDefinicaoSenha tokenEntity = tokenRepository.findByToken(token)
	            .orElseThrow(TokenInvalidoOuExpiradoException::new);

	    if (tokenEntity.isUsado() || tokenEntity.getDataExpiracao().isBefore(LocalDateTime.now())) {
	        throw new TokenInvalidoOuExpiradoException();
	    }

	    Usuario usuario = tokenEntity.getUsuario();
	    usuario.setSenha(passwordEncoder.encode(novaSenha));
	    usuarioRepository.save(usuario);

	    tokenEntity.setUsado(true);
	    tokenRepository.save(tokenEntity);
	}

	public Usuario obterUsuarioPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	public UsuarioResponseDTO editarUsuarioPorId(Long id, UsuarioUpdateRequestDTO dto) {

		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));

		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setTipo(dto.getTipo());

		Usuario usuarioSalvo = usuarioRepository.save(usuario);

		return new UsuarioResponseDTO(usuarioSalvo);
	}

	// TODO: reavaliar se esse endpoint deveria usar o id do usuário autenticado (via token)
	// em vez do id da URL, caso um dia Vendedores também precisem trocar a própria senha.
	public void alterarSenha(Long id, String antigaSenha, String novaSenha) {
	    Usuario usuario = usuarioRepository.findById(id)
	            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

	    if (!passwordEncoder.matches(antigaSenha, usuario.getSenha())) {
	        throw new CredenciaisInvalidasException();
	    }

	    usuario.setSenha(passwordEncoder.encode(novaSenha));
	    usuarioRepository.save(usuario);
	}

	public void deletarUsuarioPorId(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new UsuarioNaoEncontradoException(id);
		}
		usuarioRepository.deleteById(id);
	}
}
