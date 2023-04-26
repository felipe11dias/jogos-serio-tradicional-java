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

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ActivityRegisterResponse> edit(
            @RequestHeader(name="authorization") String token,
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ActivityRegisterRequest request
    ) throws ResponseStatusException {
        Activity activity = service.getActivity(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found."));

        Discipline discipline = service.getDiscipline(request.getIdDiscipline())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found."));

        User user = userService.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found from this token."));

        if(!Objects.equals(user.getId(), request.getIdUser())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The logged in user forbidden.");
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found."));

        User user = userService.getUserFromToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found from this token."));

        if(!Objects.equals(user.getId(), activity.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The logged in user forbidden.");
        }

        service.delete(id);

        return ResponseEntity.ok().build();

    }

}
