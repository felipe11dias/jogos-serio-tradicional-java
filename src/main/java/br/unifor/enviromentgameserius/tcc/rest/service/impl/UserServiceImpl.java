package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.config.JwtService;
import br.unifor.enviromentgameserius.tcc.domain.enums.Role;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.UserRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserProfileResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationServiceImpl authenticationService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.saveUserToken(savedUser, jwtToken);
        return UserRegisterResponse.builder()
                .id(user.getId())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
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
    public Optional<User> getUser(Long id) {
        return Optional.of(repository.findById(id)).get();
    }
}
