package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterResponse;

import java.util.List;
import java.util.Optional;

public interface DisciplineService {

    Optional<List<Discipline>> list();
    DisciplineRegisterResponse register(DisciplineRegisterRequest request, User user);
    DisciplineRegisterResponse details(Discipline discipline);
    Optional<User> getUser(Long id);
    Optional<Discipline> getDiscipline(Long id);

}
