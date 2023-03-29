package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserPerfilResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserPerfilResponse> perfil(@PathVariable(value = "id") Long id, @RequestHeader String token) {

        User userPerfil = service.getUser(id)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User from id not found."));

        User userToken = service.getUserFromToken(token)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "User from token not found."));

        if(!userPerfil.getEmail().equals(userToken.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user.");
        }
        UserPerfilResponse userPerfilResponse = UserPerfilResponse.builder()
                .name(userToken.getName())
                .email(userToken.getEmail())
                .build();

        return ResponseEntity.ok(userPerfilResponse);

    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserRegisterResponse> register(
            @Valid @RequestBody UserRegisterRequest userRegisterRequest,
            UriComponentsBuilder uriBuilder
    ) {
        UserRegisterResponse user = service.register(userRegisterRequest);
        URI uri = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
