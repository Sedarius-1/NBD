package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ClientManager {
    private final ClientRepository clientRepository;
    protected static final Logger logger = (Logger) LogManager.getLogger();

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Boolean saveClient(Client client) {
        try {
            clientRepository.add(client);
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean registerClient(Long clientId, String name, String surname, String address, LocalDate birth, BigDecimal balance) {

        try {
            if (
                    Objects.isNull(clientId) ||
                            Objects.isNull(name) ||
                            Objects.isNull(surname) ||
                            Objects.isNull(address) ||
                            Objects.isNull(birth) ||
                            Objects.isNull(balance)
            ) throw new RepositoryException("Null passed!");
            clientRepository.add(ClientFactory.createClient(clientId, name, surname, address, birth, balance));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean unregisterClient(Long clientId) {
        try {
            clientRepository.remove(clientRepository.get(clientId));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Client getClient(Long clientId) {
        Client client = null;
        try {
            client = clientRepository.get(clientId);
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return client;
    }

    public Boolean changeName(Long clientId, String name) {
        try {
            if (name != null) {
                clientRepository.modifyClientName(clientId, name);
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return false;
    }

    public Boolean changeSurname(Long clientId, String surname) {
        try {
            if (surname != null) {
                clientRepository.modifyClientSurname(clientId, surname);
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return false;
    }

    public Boolean changeAddress(Long clientId, String address) {
        try {
            if (address != null) {
                clientRepository.modifyClientAddress(clientId, address);
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return false;
    }

    public Boolean changeBirth(Long clientId, LocalDate birth) {
        try {
            if (birth != null) {
                clientRepository.modifyClientBirth(clientId, birth);
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return false;
    }

    public Boolean changeBalance(Long clientId, BigDecimal newBalance) {
        try {
            if (newBalance != null) {
                clientRepository.modifyClientBalance(clientId, newBalance);
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return false;
    }
    // TODO: access functions
}
