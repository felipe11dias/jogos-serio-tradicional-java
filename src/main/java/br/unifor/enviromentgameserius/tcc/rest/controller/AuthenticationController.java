package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return new ResponseEntity<>(authenticationService.authentication(authenticationRequest), HttpStatus.OK);
    }

}
