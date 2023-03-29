package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.config.JwtService;
import br.unifor.enviromentgameserius.tcc.domain.enums.Role;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.UserRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserPerfilResponse;
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

    private final UserRepository userRepository;
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

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return UserRegisterResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public UserPerfilResponse perfil(User userPerfil, String token) {
        String email = jwtService.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(email);

        if(!userPerfil.equals(email)) {
            return null;
        }

        return UserPerfilResponse.builder()
                .name(user.get().getName())
                .email(user.get().getEmail())
                .build();
    }

    @Override
    public Optional<User> getUserFromToken(String token) {
        String email = jwtService.extractUsername(token);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.of(userRepository.findById(id).get());
    }
}
