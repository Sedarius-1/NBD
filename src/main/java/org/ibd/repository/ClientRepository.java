package org.ibd.repository;

import jakarta.persistence.EntityManager;

public class ClientRepository {
    EntityManager entityManager;

    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
