package br.com.yggdrasil.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.UsuarioResponseDTO;
import br.com.yggdrasil.dto.UsuarioUpdateRequestDTO;
import br.com.yggdrasil.exception.UsuarioNaoEncontradoException;
import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Usuario cadastrarUsuario(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
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

	// TODO implementar verificação da senha atual utilizando o .matches
	public void alterarSenha(Long id, String novaSenha) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));

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
