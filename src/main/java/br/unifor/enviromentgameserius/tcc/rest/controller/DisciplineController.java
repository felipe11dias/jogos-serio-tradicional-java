package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.DisciplineService;
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
@RequestMapping("/api/v1/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final UserService userService;
    private final DisciplineService service;

    @GetMapping()
    public ResponseEntity<Page<DisciplineListResponse>> list(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) throws ResponseStatusException {
        return ResponseEntity.ok(service.list(pagination));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discipline> details(@PathVariable(value = "id") Long id) throws ResponseStatusException {
        return ResponseEntity.ok(service.getDiscipline(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found.")));

    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<DisciplineRegisterResponse> register(
            @Valid @RequestBody DisciplineRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) throws ResponseStatusException {
        User user = service.getUser(request.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline register invalid. User not found."));

        DisciplineRegisterResponse discipline = service.register(request, user);
        URI uri = uriBuilder.path("/api/v1/disciplines/{id}").buildAndExpand(discipline.getId()).toUri();
        return ResponseEntity.created(uri).body(discipline);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DisciplineRegisterResponse> edit(
            @RequestHeader(name="authorization") String token,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody DisciplineRegisterRequest request
    ) throws ResponseStatusException {
        Discipline discipline = service.getDiscipline(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found."));

        User user = userService.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found from this token."));

        if(!Objects.equals(user.getId(), request.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The logged in user forbidden.");
        }

        return ResponseEntity.ok().body(service.edit(discipline, request));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @RequestHeader(name="authorization") String token,
            @PathVariable(value = "id") Long id
    ) throws ResponseStatusException {

        Discipline discipline = service.getDiscipline(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found."));

        User user = userService.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found from this token."));

        if(!Objects.equals(user.getId(), discipline.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The logged in user forbidden.");
        }

        service.delete(id);

        return ResponseEntity.ok().build();

    }

}
