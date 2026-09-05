package br.com.yggdrasil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yggdrasil.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
