package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.config.JwtService;
import br.unifor.enviromentgameserius.tcc.domain.enums.Role;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.UserRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
