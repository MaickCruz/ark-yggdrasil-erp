package br.com.yggdrasil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yggdrasil.model.entity.TokenDefinicaoSenha;

public interface TokenDefinicaoSenhaRepository extends JpaRepository<TokenDefinicaoSenha, Long> {
    Optional<TokenDefinicaoSenha> findByToken(String token);
}