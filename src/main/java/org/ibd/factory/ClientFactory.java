package org.ibd.factory;

import lombok.NoArgsConstructor;
import org.ibd.model.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
public class ClientFactory {
    public static Client createClient(Long clientId, String name, String surname, String address, LocalDate birth, BigDecimal balance) {
        return new Client(clientId, name, surname, address, birth, balance);
    }
}
