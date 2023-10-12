package org.ibd.factory;

import org.ibd.model.clients.Client;

import java.time.LocalDate;

public class ClientFactory {
    public static Client createClient(Long clientId, String name, String surname, String address, LocalDate birth) {
        return new Client(clientId, name, surname, address, birth);
    }
}
