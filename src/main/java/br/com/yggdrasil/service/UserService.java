package br.com.yggdrasil.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.yggdrasil.dto.UserResponseDTO;
import br.com.yggdrasil.dto.UserUpdateRequestDTO;
import br.com.yggdrasil.exception.InvalidCredentialsException;
import br.com.yggdrasil.exception.EmailAlreadyExistsException;
import br.com.yggdrasil.exception.InvalidOrExpiredTokenException;
import br.com.yggdrasil.exception.UserNotFoundException;
import br.com.yggdrasil.model.entity.PasswordSetupToken;
import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.repository.PasswordSetupTokenRepository;
import br.com.yggdrasil.repository.UserRepository;

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
    
    @Value("${app.base-url}")
    private String baseUrl;

    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        String temporaryPassword = UUID.randomUUID().toString();

        user.setPassword(passwordEncoder.encode(temporaryPassword));

        User savedUser = userRepository.save(user);

        generatePasswordSetupToken(savedUser);

        return savedUser;
    }

    private void generatePasswordSetupToken(User user) {

        String token = UUID.randomUUID().toString();

        PasswordSetupToken tokenEntity = new PasswordSetupToken();

        tokenEntity.setToken(token);
        tokenEntity.setUser(user);
        tokenEntity.setExpirationDate(LocalDateTime.now().plusHours(24));

        tokenRepository.save(tokenEntity);

        String setupPasswordUrl =
                baseUrl + "/auth/set-password?token=" + token;

        emailService.sendPasswordSetupEmail(
                user.getEmail(),
                user.getName(),
                setupPasswordUrl
        );
    }

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

    // TODO: Review whether this operation should use the authenticated user's ID
    // instead of the ID from the URL if salespeople are allowed to change
    // their own passwords in the future.
    public void changePassword(
            Long id,
            String currentPassword,
            String newPassword) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    
    
    public void deleteUserById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
    }
}