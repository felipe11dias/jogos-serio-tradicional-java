package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.DisciplineRepository;
import br.unifor.enviromentgameserius.tcc.domain.repository.UserRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository repository;

    private final UserRepository userRepository;

    @Override
    public Optional<List<Discipline>> list() {
        return Optional.of(repository.findAll());
    }

    @Override
    public DisciplineRegisterResponse register(DisciplineRegisterRequest request, User user) {
        var discipline = Discipline.builder()
                .name(request.getName())
                .theme(request.getTheme())
                .user(user)
                .activities(Collections.emptyList())
                .build();

        repository.save(discipline);

        return DisciplineRegisterResponse.builder()
                .id(discipline.getId())
                .name(discipline.getName())
                .theme(discipline.getTheme())
                .idUser(discipline.getUser().getId())
                .activities(discipline.getActivities())
                .build();
    }

    @Override
        public DisciplineRegisterResponse details(Discipline discipline) {

        return DisciplineRegisterResponse.builder()
                .id(discipline.getId())
                .name(discipline.getName())
                .theme(discipline.getTheme())
                .activities(discipline.getActivities())
                .build();
    }

    @Override
    public Optional<Discipline> getDiscipline(Long id) {
        return Optional.of(repository.findById(id).get());
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.of(userRepository.findById(id).get());
    }

}
