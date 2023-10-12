package org.ibd.repository;

import jakarta.persistence.EntityManager;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.purchases.Purchase;

import java.util.List;


public class PurchaseRepository {
    EntityManager entityManager;

    public PurchaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persistPurchase(final Purchase purchase) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(purchase);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public final Purchase getPurchase(Long purchaseId) throws RepositoryException {
        try {
            return entityManager.createQuery("SELECT p FROM Purchase p WHERE p.purchaseId = :providedPurchaseId", Purchase.class).setParameter("providedPurchaseId", purchaseId).getSingleResult();
        } catch (Exception e) {

            throw new RepositoryException(e.toString());
        }
    }


    public final List<Purchase> getAllPurchases() throws RepositoryException {
        try {
            return entityManager.createQuery("SELECT p FROM Purchase p", Purchase.class).getResultList();
        } catch (Exception e) {

            throw new RepositoryException(e.toString());
        }
    }

    public final List<Purchase> getAllPurchasesForSingleClient(Long clientId) throws RepositoryException {
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Purchase p WHERE p.client.clientId = :providedClientId", Purchase.class)
                    .setParameter("providedClientId", clientId).getResultList();
        } catch (Exception e) {

            throw new RepositoryException(e.toString());
        }
    }

    public void removePurchase(Purchase purchase) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(purchase);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }
}
