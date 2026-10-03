package br.com.yggdrasil.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yggdrasil.model.entity.PasswordSetupToken;
import br.com.yggdrasil.model.entity.User;

public interface PasswordSetupTokenRepository extends JpaRepository<PasswordSetupToken, Long> {
    Optional<PasswordSetupToken> findByToken(String token);
    List<PasswordSetupToken> findAllByUserAndUsedFalse(User user);
}