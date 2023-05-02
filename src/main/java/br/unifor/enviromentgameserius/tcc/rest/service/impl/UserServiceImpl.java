package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.config.JwtService;
import br.unifor.enviromentgameserius.tcc.domain.enums.Role;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.UserRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserProfileResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationServiceImpl authenticationService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Objects.equals(request.getRole(), "STUDENT") ? Role.STUDENT : Role.TEACHER)
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.saveUserToken(savedUser, jwtToken);
        return UserRegisterResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String sendEmail(User user, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Olá, segue sua nova senha " + newPassword + " do sistema JOGOS SERIOS TRADICIONAIS." );
        message.setTo(user.getEmail());
        message.setFrom("wolmirgarbin@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }

    @Override
    public UserProfileResponse perfil(User userPerfil, String token) {
        String email = jwtService.extractUsername(token);
        Optional<User> user = repository.findByEmail(email);

        if(!userPerfil.getEmail().equals(email) || user.isEmpty()) {
            return null;
        }

        return UserProfileResponse.builder()
                .name(user.get().getName())
                .email(user.get().getEmail())
                .build();
    }

    @Override
    public Optional<User> getUserFromToken(String token) {
        String email = jwtService.extractUsernameBearer(token);
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserFromEmail(String email) {
        return Optional.of(repository.findByEmail(email)).get();
    }

    @Override
    @Transactional
    public String newPasswordUser(User user) {
        String newPassword = generatePassayPassword();
        user.setPassword(passwordEncoder.encode(newPassword));

        repository.save(user);
        return newPassword;
    }

    @Override
    public String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "Error generator password";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.of(repository.findById(id)).get();
    }
}
