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
        clientRepository.add(client1); // TODO: add doesNotThrow assert

        Client client2 = clientRepository.get(1L);
        assertNotNull(client2);
        assertEquals(client2.getClientId(), 1L);
        assertEquals(client2.getName(), "Name");
        assertEquals(client2.getSurname(), "Surname");
        assertEquals(client2.getAddress(), "Address");
        assertEquals(client2.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client2.getBalance(), BigDecimal.ZERO);
        assertEquals(client2.getPurchaseSet().size(), 0);

        clientRepository.remove(client1);

        assertThrows(RepositoryException.class, () -> clientRepository.get(1L));

        entityManagerFactory.close();
    }

    @Test
    void ClientRepositoryClientModifyTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(entityManager);
        Client client1 = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
        clientRepository.add(client1);

        clientRepository.modifyClientName(1L, "Name2");
        clientRepository.modifyClientSurname(1L, "Surname2");
        clientRepository.modifyClientAddress(1L, "Address2");
        clientRepository.modifyClientBirth(1L, LocalDate.of(2222, 11, 22));
        clientRepository.modifyClientBalance(1L, new BigDecimal(2137));

        Client client2 = clientRepository.get(1L);
        assertNotNull(client2);
        assertEquals(client2.getClientId(), 1L);
        assertEquals("Name2", client2.getName());
        assertEquals("Surname2", client2.getSurname());
        assertEquals("Address2", client2.getAddress(), "Address2");
        assertEquals(LocalDate.of(2222, 11, 22), client2.getBirth());
        assertEquals(new BigDecimal(2137), client2.getBalance());

        entityManagerFactory.close();
    }

    // TODO: modify tests
    // TODO: bad case tests
}
