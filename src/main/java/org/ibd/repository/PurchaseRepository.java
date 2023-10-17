package org.ibd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.purchases.Purchase;

import java.util.List;


public class PurchaseRepository implements Repository<Purchase> {
    EntityManager entityManager;

    public PurchaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(final Purchase purchase) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(purchase);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public final Purchase get(Long purchaseId) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT purchase FROM Purchase purchase WHERE purchase.purchaseId = :providedPurchaseId");
            query.setParameter("providedPurchaseId", purchaseId);
            Purchase purchase = (Purchase) query.getSingleResult();
            entityManager.getTransaction().commit();
            return purchase;
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

    public void remove(Purchase purchase) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Long purchaseId = purchase.getPurchaseId();
            Query query = entityManager.createQuery("DELETE FROM Purchase purchase WHERE purchase.purchaseId = :providedPurchaseId");
            query.setParameter("providedPurchaseId", purchaseId);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }
}
