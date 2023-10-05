package org.ibd.repository;

import jakarta.persistence.EntityManager;

public class PurchaseRepository {
    EntityManager entityManager;

    public PurchaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
