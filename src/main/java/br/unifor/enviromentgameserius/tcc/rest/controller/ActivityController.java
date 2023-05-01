package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.*;
import br.unifor.enviromentgameserius.tcc.rest.service.ActivityService;
import br.unifor.enviromentgameserius.tcc.rest.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final UserService userService;
    private final ActivityService service;

    @GetMapping()
    public ResponseEntity<Page<ActivityListResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) throws ResponseStatusException {
        return ResponseEntity.ok(service.list(pagination));
    }

    @GetMapping("/discipline/{idDiscipline}")
    public ResponseEntity<Page<ActivityListResponse>> listByDiscipline(@PathVariable(value = "idDiscipline") Long idDiscipline, @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) throws ResponseStatusException {
        Discipline discipline = service.getDiscipline(idDiscipline)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada."));

        return ResponseEntity.ok(service.listByDiscipline(discipline, pagination));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable(value = "id") Long id) {
        Activity activity = service.getActivity(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade não encontrada."));

        ActivityResponse response = new ActivityResponse(activity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<ActivityRegisterResponse> register(
            @Valid @RequestBody ActivityRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        Discipline discipline = service.getDiscipline(Long.valueOf(request.getIdDiscipline()))
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade é inválida. Disciplina não encontrada."));

        User user = service.getUser(request.getIdUser())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade é inválida. Usuário não encontrado."));

        ActivityRegisterResponse activity = service.register(request, discipline, user);
        URI uri = uriBuilder.path("/api/v1/activities/{id}").buildAndExpand(activity.getId()).toUri();
        return ResponseEntity.created(uri).body(activity);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ActivityRegisterResponse> edit(
            @RequestHeader(name="authorization") String token,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ActivityEditRequest request
    ) throws ResponseStatusException {
        Activity activity = service.getActivity(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade não encontrada."));

        Discipline discipline = service.getDiscipline(Long.valueOf(request.getIdDiscipline()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada."));

        User user = userService.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token informado é inválido."));

        if(!Objects.equals(user.getId(), request.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O usuário logado não tem permissão para essa ação.");
        }

        return ResponseEntity.ok().body(service.edit(activity, discipline, request));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @RequestHeader(name="authorization") String token,
            @PathVariable(value = "id") Long id
    ) throws ResponseStatusException {
        Activity activity = service.getActivity(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atividade não encontrada."));

        User user = userService.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token informado é inválido."));

        if(!Objects.equals(user.getId(), activity.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O usuário logado não tem permissão para essa ação.");
        }

        service.delete(id);

        return ResponseEntity.ok().build();

    }

}
