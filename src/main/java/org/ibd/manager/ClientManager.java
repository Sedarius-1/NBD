package org.ibd.manager;

import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ClientManager {
    private final ClientRepository clientRepository;
    Logger log = LoggerFactory.getLogger("NBD");

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //Create
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
            log.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    //Read
    public Client getClient(Long clientId) {
        Client client = null;
        try {
            client = clientRepository.get(clientId);
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return client;
    }

    public ArrayList<Client> findClients(Bson finder) {
        ArrayList<Client> clientList;
        clientList = clientRepository.find(finder);
        return clientList;
    }

    public ArrayList<Client> getAllClients() {
        ArrayList<Client> clientList;
        clientList = clientRepository.getAll();
        return clientList;
    }

    //Update
    public Boolean changeName(Long clientId, String name) {
        try {
            if (name != null) {
                clientRepository.updateOne(clientId, Updates.set("name", name));
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return false;
    }

    public Boolean changeSurname(Long clientId, String surname) {
        try {
            if (surname != null) {
                clientRepository.updateOne(clientId, Updates.set("surname", surname));
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return false;
    }

    public Boolean changeAddress(Long clientId, String address) {
        try {
            if (address != null) {
                clientRepository.updateOne(clientId, Updates.set("address", address));
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return false;
    }

    public Boolean changeBirth(Long clientId, LocalDate birth) {
        try {
            if (birth != null) {
                clientRepository.updateOne(clientId, Updates.set("birth", birth));
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return false;
    }

    public Boolean changeBalance(Long clientId, BigDecimal newBalance) {
        try {
            if (newBalance != null) {
                clientRepository.updateOne(clientId, Updates.set("balance", newBalance));
            } else throw new RepositoryException("null passed");
            return true;
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return false;
    }

    //Delete
    public Boolean unregisterClient(Long clientId) {
        try {
            clientRepository.remove(clientId);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


}
