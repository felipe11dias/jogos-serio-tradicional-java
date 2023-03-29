package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.DisciplineRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineListResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.DisciplineService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/disciplines")
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineService service;

    @GetMapping()
    public ResponseEntity<List<Discipline>> list() {
        return ResponseEntity.ok(service.list()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplines not found.")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discipline> details(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.getDiscipline(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found.")));

    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<DisciplineRegisterResponse> registerDiscipline(
            @Valid @RequestBody DisciplineRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        User user = service.getUser(request.getIdUser())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity register invalid. User not found."));

        DisciplineRegisterResponse discipline = service.register(request, user);
        URI uri = uriBuilder.path("/api/v1/disciplines/{id}").buildAndExpand(discipline.getId()).toUri();
        return ResponseEntity.created(uri).body(discipline);
    }

}
