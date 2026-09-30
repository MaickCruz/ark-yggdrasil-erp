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

import br.com.yggdrasil.dto.AlterarSenhaRequestDTO;
import br.com.yggdrasil.dto.UsuarioResponseDTO;
import br.com.yggdrasil.dto.UsuarioUpdateRequestDTO;
import br.com.yggdrasil.model.entity.Usuario;
import br.com.yggdrasil.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {

		Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
		UsuarioResponseDTO dto = new UsuarioResponseDTO(usuarioSalvo);

		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> obterUsuarioPorId(@PathVariable Long id) {
		UsuarioResponseDTO usuario = new UsuarioResponseDTO(usuarioService.obterUsuarioPorId(id));
		return ResponseEntity.ok(usuario);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> editarUsuarioPorId(
	        @PathVariable Long id,
	        @Valid @RequestBody UsuarioUpdateRequestDTO dto) {

	    UsuarioResponseDTO resposta =
	            usuarioService.editarUsuarioPorId(id, dto);

	    return ResponseEntity.ok(resposta);
	}

	// TODO: reavaliar se esse endpoint deveria usar o id do usuário autenticado (via token)
	// em vez do id da URL, caso um dia Vendedores também precisem trocar a própria senha.
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PutMapping("/{id}/senha")
	public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @Valid @RequestBody AlterarSenhaRequestDTO dto) {
		usuarioService.alterarSenha(id, dto.getAntigaSenha(), dto.getNovaSenha());
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id) {
		usuarioService.deletarUsuarioPorId(id);
		return ResponseEntity.noContent().build();
	}

}
