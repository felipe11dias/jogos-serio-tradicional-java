package br.unifor.enviromentgameserius.tcc.rest.controller;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Question;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.ActivityService;
import br.unifor.enviromentgameserius.tcc.rest.service.RankingService;
import br.unifor.enviromentgameserius.tcc.rest.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RankingController {

    private final UserService userService;
    private final ActivityService activityService;
    private final RankingService service;

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<RankingRegisterResponse> register(
            @Valid @RequestBody RankingRegisterRequest request,
            UriComponentsBuilder uriBuilder
    ) throws ResponseStatusException {
        User user = userService.getUser(request.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classificação é inválida. Usuário não encontrado."));

        Activity activity = activityService.getActivity(request.getIdActivity())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classificação é inválida. Atividade não encontrada."));

        List<Question> questions = service.getQuestions(request.getQuestionsHit());

        RankingRegisterResponse ranking = service.register(request, questions, user, activity);
        URI uri = uriBuilder.path("/api/v1/ratings/{id}").buildAndExpand(ranking.getId()).toUri();
        return ResponseEntity.created(uri).body(ranking);
    }

}
