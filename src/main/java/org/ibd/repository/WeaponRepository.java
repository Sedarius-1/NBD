package org.ibd.repository;

import jakarta.persistence.EntityManager;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Weapon;

import java.util.List;

public class WeaponRepository implements Repository<Weapon> {
    EntityManager entityManager;

    public WeaponRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(final Weapon weapon) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(weapon);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public final Weapon get(Long serialNumber) throws RepositoryException {
        try {
            return entityManager.createQuery(
                            "SELECT w FROM Weapon w WHERE w.serialNumber = :providedSerialNumber", Weapon.class)
                    .setParameter("providedSerialNumber", serialNumber).getSingleResult();
        } catch (Exception e) {
            throw new RepositoryException(e.toString());
        }
    }

    public void remove(Weapon weapon) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(weapon);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public final List<Weapon> getAllWeapons() throws RepositoryException {
        try {
            return entityManager.createQuery("SELECT w FROM Weapon w", Weapon.class).getResultList();
        } catch (Exception e) {

            throw new RepositoryException(e.toString());
        }
    }

    // TODO: add `edit` methods if needed (too lazy to add then now)
}
