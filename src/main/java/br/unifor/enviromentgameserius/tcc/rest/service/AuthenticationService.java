package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    AuthenticationResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    );

}
