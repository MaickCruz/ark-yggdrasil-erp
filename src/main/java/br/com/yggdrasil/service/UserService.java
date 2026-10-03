package br.com.yggdrasil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.UserCreateRequestDTO;
import br.com.yggdrasil.dto.UserResponseDTO;
import br.com.yggdrasil.dto.UserUpdateRequestDTO;
import br.com.yggdrasil.exception.EmailAlreadyExistsException;
import br.com.yggdrasil.exception.InvalidOrExpiredTokenException;
import br.com.yggdrasil.exception.UserNotFoundException;
import br.com.yggdrasil.model.entity.PasswordSetupToken;
import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.repository.PasswordSetupTokenRepository;
import br.com.yggdrasil.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordSetupTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PasswordSetupTokenRepository tokenRepository,
            EmailService emailService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }
    
    @Value("${app.frontend-url}")
    private String frontendUrl;

    public UserResponseDTO createUser(UserCreateRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        String unusablePassword = UUID.randomUUID().toString();

        user.setPassword(
                passwordEncoder.encode(unusablePassword)
        );

        User savedUser = userRepository.save(user);

        sendPasswordSetupEmail(savedUser);

        return new UserResponseDTO(savedUser);
    }

    private void sendPasswordSetupEmail(User user) {

        invalidatePreviousPasswordTokens(user);

        String token = UUID.randomUUID().toString();

        PasswordSetupToken tokenEntity = new PasswordSetupToken();

        tokenEntity.setToken(token);
        tokenEntity.setUser(user);
        tokenEntity.setExpirationDate(
                LocalDateTime.now().plusHours(24)
        );

        tokenRepository.save(tokenEntity);

        String setupPasswordUrl =
                frontendUrl + "/set-password?token=" + token;

        emailService.sendPasswordSetupEmail(
                user.getEmail(),
                user.getName(),
                setupPasswordUrl
        );
    }

    @Transactional
    public void setPassword(String token, String newPassword) {

        PasswordSetupToken tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(InvalidOrExpiredTokenException::new);

        if (tokenEntity.isUsed()
                || tokenEntity.getExpirationDate().isBefore(LocalDateTime.now())) {

            throw new InvalidOrExpiredTokenException();
        }

        User user = tokenEntity.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        tokenEntity.setUsed(true);
        tokenRepository.save(tokenEntity);
    }
    
    private void invalidatePreviousPasswordTokens(User user) {

        List<PasswordSetupToken> tokens =
                tokenRepository.findAllByUserAndUsedFalse(user);

        tokens.forEach(token -> token.setUsed(true));

        tokenRepository.saveAll(tokens);
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO updateUserById(
            Long id,
            UserUpdateRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser);
    }
    
    public void requestPasswordReset(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        sendPasswordSetupEmail(user);
    }

    
    public void deleteUserById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }
}