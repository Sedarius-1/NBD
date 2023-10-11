package org.ibd.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;

import java.time.LocalDate;
import java.util.Optional;


public class ClientRepository {
    EntityManager entityManager;
    protected static final Logger logger = (Logger) LogManager.getLogger();


    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        logger.debug("Created ClientRepository");
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
            return entityManager.createQuery("SELECT c FROM Client c WHERE c.clientId = :providedClientId", Client.class).setParameter("providedClientId", clientId).getSingleResult();
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
            entityManager.remove(client);
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
            entityManager.remove(client);
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
            entityManager.remove(client);
            client.setBirth(birth);
            entityManager.persist(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RepositoryException(e.toString());
        }
    }


}
