package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.ActivityRepository;
import br.unifor.enviromentgameserius.tcc.domain.repository.DisciplineRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.*;
import br.unifor.enviromentgameserius.tcc.rest.service.ActivityService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService service;

    @GetMapping()
    public ResponseEntity<List<Activity>> list() {
        return ResponseEntity.ok(service.list()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activities not found.")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.getActivity(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found.")));
    }

    @PostMapping("register")
    @Transactional
    public ResponseEntity<ActivityRegisterResponse> register(
            @Valid @RequestBody ActivityRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        Discipline discipline = service.getDiscipline(request.getIdDiscipline())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity register invalid. Discipline not found."));

        User user = service.getUser(request.getIdUser())
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity register invalid. User not found."));

        ActivityRegisterResponse activity = service.register(request, discipline, user);
        URI uri = uriBuilder.path("/api/v1/activities/{id}").buildAndExpand(activity.getId()).toUri();
        return ResponseEntity.created(uri).body(activity);
    }

}
