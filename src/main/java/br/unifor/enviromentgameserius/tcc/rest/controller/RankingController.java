package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Ranking;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.ActivityService;
import br.unifor.enviromentgameserius.tcc.rest.service.RankingService;
import br.unifor.enviromentgameserius.tcc.rest.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RankingController {

    private final UserService userService;
    private final ActivityService activityService;
    private final RankingService service;

    @GetMapping()
    public ResponseEntity<Page<RankingListResponse>> list(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Pageable pagination = PageRequest.of(page, 10, Sort.by("id").ascending());

        Page<RankingListResponse> ratings = service.list(pagination);
        return ResponseEntity.ok(ratings);
    }

    @Transactional
    @PostMapping("/register-or-edit")
    public ResponseEntity<RankingRegisterResponse> registerOrEdit(
            @Valid @RequestBody RankingRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) throws ResponseStatusException {
        User user = userService.getUser(request.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classificação é inválida. Usuário não encontrado."));

        Activity activity = activityService.getActivity(request.getIdActivity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classificação é inválida. Atividade não encontrada."));

        Optional<Ranking> rankingOptional = service.getRankinByUserAndActivity(user, activity);
        rankingOptional.ifPresent(ranking -> service.delete(ranking.getId()));

        RankingRegisterResponse ranking = service.register(request, user, activity);
        URI uri = uriBuilder.path("/api/v1/ratings/{id}").buildAndExpand(ranking.getId()).toUri();
        return ResponseEntity.created(uri).body(ranking);
    }

}
