package org.ibd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ClientRepository {
    EntityManager entityManager;

    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addClient(final Client client) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public final Client getClient(Long clientId) throws RepositoryException {
        try {
            return entityManager.find(Client.class, clientId, LockModeType.PESSIMISTIC_WRITE);
        } catch (Exception e) {

            throw new RepositoryException(e.toString());
        }
    }

    public void removeClient(Client client) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientName(Long clientId, String name) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = getClient(clientId);

            client.setName(name);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientSurname(Long clientId, String surname) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = getClient(clientId);
            client.setSurname(surname);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientAddress(Long clientId, String address) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = getClient(clientId);
            client.setAddress(address);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientAddress(Long clientId, LocalDate birth) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = getClient(clientId);
            client.setBirth(birth);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }

    public void modifyClientBalance(Long clientId, BigDecimal newBalance) throws RepositoryException {
        try {
            entityManager.getTransaction().begin();
            Client client = getClient(clientId);
            client.setBalance(newBalance);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }


}
