package org.ibd.repository;

import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Weapon;

public class PurchaseRepository {
    EntityManager entityManager;
    protected static final Logger logger = (Logger) LogManager.getLogger();
    public PurchaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addPurchase(final Purchase purchase) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(purchase);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }
}
