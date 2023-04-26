package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserProfileResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterResponse;

import java.util.Optional;

public interface UserService {

    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);
    Optional<User> getUser(Long id);
    UserProfileResponse perfil(User userPerfil, String token);
    Optional<User> getUserFromToken(String token);

}
