package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.DisciplineRegisterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DisciplineService {

    Page<DisciplineListResponse> list(Pageable pagination);
    DisciplineRegisterResponse register(DisciplineRegisterRequest request, User user);
    DisciplineRegisterResponse edit(Discipline discipline, DisciplineRegisterRequest request);
    void delete(Long id);
    DisciplineRegisterResponse details(Discipline discipline);
    Optional<User> getUser(Long id);
    Optional<Discipline> getDiscipline(Long id);

}
