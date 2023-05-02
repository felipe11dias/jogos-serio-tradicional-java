package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.RefreshTokenRequest;
import br.unifor.enviromentgameserius.tcc.rest.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return new ResponseEntity<>(service.authenticate(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody RefreshTokenRequest body
    ) throws IOException {
        return new ResponseEntity<>(service.refreshToken(body, request, response), HttpStatus.OK);
    }

}
