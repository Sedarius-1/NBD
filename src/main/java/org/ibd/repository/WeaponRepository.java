package org.ibd.repository;

import jakarta.persistence.EntityManager;

public class WeaponRepository {
    EntityManager entityManager;

    public WeaponRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
