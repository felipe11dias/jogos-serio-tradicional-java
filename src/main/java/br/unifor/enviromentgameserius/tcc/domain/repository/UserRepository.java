package br.unifor.enviromentgameserius.tcc.domain.repository;

import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    Optional<User> findByEmail(String email);
}
