package org.ibd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;
import org.apache.commons.lang.NullArgumentException;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class ClientRepository implements Repository<Client> {
    EntityManager entityManager;

    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void add(final Client client) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public final Client get(Long clientId) throws RepositoryException {
        try {

            Query query = entityManager.createQuery("SELECT client FROM Client client WHERE client.clientId = :providedClientId");
            query.setParameter("providedClientId", clientId);
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

            return (Client) query.getSingleResult();
        } catch (Exception e) {

            throw new RepositoryException(e.toString());
        }
    }

    public final Client getTransactive(Long clientId) throws RepositoryException {
        try {
            if(Objects.isNull(clientId)){
                throw new NullArgumentException("Null passed as Id");
            }
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT client FROM Client client WHERE client.clientId = :providedClientId");
            query.setParameter("providedClientId", clientId);
            entityManager.getTransaction().commit();
            return (Client) query.getSingleResult();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void remove(Client client) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Long clientId = client.getClientId();
            client.getPurchaseSet().clear();
            Query query = entityManager.createQuery("DELETE FROM Client client WHERE client.clientId = :providedClientId");
            query.setParameter("providedClientId", clientId);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientName(Long clientId, String name) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = get(clientId);
            Client merge = entityManager.merge(client);
            merge.setName(name);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientSurname(Long clientId, String surname) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = get(clientId);
            Client merge = entityManager.merge(client);
            merge.setSurname(surname);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientAddress(Long clientId, String address) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = get(clientId);
            Client merge = entityManager.merge(client);
            merge.setAddress(address);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientBirth(Long clientId, LocalDate birth) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = get(clientId);
            Client merge = entityManager.merge(client);
            merge.setBirth(birth);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientBalance(Long clientId, BigDecimal newBalance) throws RepositoryException {
        try {

            if(!entityManager.getTransaction().isActive()){
                entityManager.getTransaction().begin();
            }
            Client client = get(clientId);
            Client merge = entityManager.merge(client);
            merge.setBalance(newBalance);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

}
