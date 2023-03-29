package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.UserRegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);
}
