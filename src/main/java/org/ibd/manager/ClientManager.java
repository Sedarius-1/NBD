package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClientManager {
    private final ClientRepository clientRepository;
    protected static final Logger logger = (Logger) LogManager.getLogger();

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Boolean registerClient(Long clientId, String name, String surname, String address, LocalDate birth, BigDecimal balance) {
        // TODO: add auto-generation of clientId
        try {
            clientRepository.addClient(ClientFactory.createClient(clientId, name, surname, address, birth, balance));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean unregisterClient(Long clientId) {
        try {
            clientRepository.removeClient(clientRepository.getClient(clientId));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Client getClient(Long clientId) {
        Client client = null;
        try {
            client = clientRepository.getClient(clientId);
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return client;
    }

    public Boolean changeBalance(Client client, BigDecimal newBalance){
        try {
            clientRepository.modifyClientBalance(client.getClientId(), newBalance);
            return true;
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return false;
    }
    // TODO: access functions
}
