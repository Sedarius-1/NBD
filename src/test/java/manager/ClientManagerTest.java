package manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.manager.ClientManager;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientManagerTest {

    @Test
    void PersistClientTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
        clientManager.registerClient(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
        Client client = clientManager.getClient(1L);
        assertNotNull(client);
        assertEquals(client.getClientId(), 1L);
        assertEquals(client.getName(), "Name");
        assertEquals(client.getSurname(), "Surname");
        assertEquals(client.getAddress(), "Address");
        assertEquals(client.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client.getPurchaseSet().size(), 0);
        entityManagerFactory.close();
    }

    // TODO: add bad case tests
}
