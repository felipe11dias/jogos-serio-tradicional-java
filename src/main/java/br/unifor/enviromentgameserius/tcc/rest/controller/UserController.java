package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.RecoverPasswordRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserProfileResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("profile")
    public ResponseEntity<UserProfileResponse> profile(
            @RequestHeader(name="authorization") String token
    ) {
        var user = service.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token informado é inválido."));

        UserProfileResponse userProfileResponse = UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

        return ResponseEntity.ok(userProfileResponse);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserRegisterResponse> register(
            @Valid @RequestBody UserRegisterRequest userRegisterRequest,
            UriComponentsBuilder uriBuilder
    ) {
        List<String> roles = new ArrayList<>();
        roles.add("STUDENT");
        roles.add("TEACHER");
        if(!roles.contains(userRegisterRequest.getRole())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Perfil não existe.");
        }

        Optional<User> userExist = service.getUserFromEmail(userRegisterRequest.getEmail());
        if(userExist.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já existe.");
        }

        UserRegisterResponse user = service.register(userRegisterRequest);
        URI uri = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/recover-password")
    @Transactional
    public ResponseEntity<String> recoverPassword
            (
            @Valid @RequestBody RecoverPasswordRequest request
    ) {
        User user = service.getUserFromEmail(request.getEmail())
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado."));

        String newPassword = service.newPasswordUser(user);
        return ResponseEntity.ok(service.sendEmail(user, newPassword));
    }
}
