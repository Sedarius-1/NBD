package org.cassandra;

import org.cassandra.model.Client;
import org.cassandra.repo.GeneralRepo;
import org.cassandra.repo.SessionController;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Client client = new Client(
                1L,
                "jan",
                "kowalski",
                "tutaj",
                LocalDate.now(),
                new BigDecimal("200"));

        try (SessionController sessionController = new SessionController()) {

            GeneralRepo<Client> clientRepository =
                    sessionController.createClassRepository(Enums.accessType.CLIENT);

            clientRepository.insert(client);

            Client selectedClient = clientRepository.select(client.getClientId(),
                    Enums.classType.CLIENT);

            client.setName("Maciek");
            clientRepository.update(client);

            Client selectedClient2 = clientRepository.select(client.getClientId(),
                    Enums.classType.CLIENT);

            System.out.println(selectedClient2);
            clientRepository.delete(client);
        } catch (Exception e) {

        throw new RuntimeException(e);
    }
}
}
