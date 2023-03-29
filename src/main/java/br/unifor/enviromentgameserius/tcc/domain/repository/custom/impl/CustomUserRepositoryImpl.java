package br.unifor.enviromentgameserius.tcc.domain.repository.custom.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.custom.CustomUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User customFindMethod(Long id) {
        return (User) entityManager.createQuery("FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}