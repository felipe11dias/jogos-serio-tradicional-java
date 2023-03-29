package br.unifor.enviromentgameserius.tcc.domain.repository.custom;

import br.unifor.enviromentgameserius.tcc.domain.model.User;

public interface CustomUserRepository {
    User customFindMethod(Long id);
}
