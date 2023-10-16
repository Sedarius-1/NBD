package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    @Test
    void ClientRepositoryCreateClientTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(entityManager);
        Client client1 = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
        clientRepository.addClient(client1); // TODO: add doesNotThrow assert

        Client client2 = clientRepository.getClient(1L); // client repo may be fuk
        assertNotNull(client2);
        assertEquals(client2.getClientId(), 1L);
        assertEquals(client2.getName(), "Name");
        assertEquals(client2.getSurname(), "Surname");
        assertEquals(client2.getAddress(), "Address");
        assertEquals(client2.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client2.getBalance(), BigDecimal.ZERO);
        assertEquals(client2.getPurchaseSet().size(), 0);

        clientRepository.removeClient(client2);

        assertNull(clientRepository.getClient(1L));

        entityManagerFactory.close();
    }

    // TODO: modify tests
    // TODO: bad case tests
}
