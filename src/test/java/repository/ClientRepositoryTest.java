package repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ClientRepositoryTest {

    /* CREATE TESTS */
    @Test
    @Order(1)
    void ClientRepositoryCreateClientSuccessTest() throws RepositoryException {

        try (ClientRepository clientRepository = new ClientRepository()) {
            Client client1 = new Client(1L, "Name", "Surname",
                    "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
            clientRepository.add(client1);

            Client client2 = clientRepository.get(1L);
            assertNotNull(client2);
            assertEquals(client2.getClientId(), 1L);
            assertEquals(client2.getName(), "Name");
            assertEquals(client2.getSurname(), "Surname");
            assertEquals(client2.getAddress(), "Address");
            assertEquals(client2.getBirth(), LocalDate.of(2000, 1, 1));
            assertEquals(client2.getBalance(), BigDecimal.ZERO);

            clientRepository.remove(1L);

            assertThrows(RepositoryException.class, () -> clientRepository.get(1L));
        }

    }

    @Test
    @Order(2)
    void ClientRepositoryCreateClientFailureTest() {

        try (ClientRepository clientRepository = new ClientRepository()) {
            assertThrows(RepositoryException.class, () -> clientRepository.add(null));
        }
    }

    /* READ TESTS*/
    @Test
    @Order(3)
    void ClientRepositoryGetClientSuccessTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertDoesNotThrow(() -> clientRepository.add(new Client(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0))));
            assertDoesNotThrow(() -> clientRepository.get(1L));
        }
    }

    @Test
    @Order(4)
    void ClientRepositoryGetClientFailureTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertThrows(RepositoryException.class, () -> clientRepository.get(1L));
        }
    }

    @Test
    @Order(5)
    void ClientRepositoryGetAllClientTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertEquals(0, clientRepository.getAll().size());
            assertDoesNotThrow(() -> clientRepository.add(new Client(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0))));
            assertDoesNotThrow(() -> clientRepository.add(new Client(2L, "ntest2", "stest2", "atest2", LocalDate.of(2002, 1, 1), new BigDecimal(10))));
            assertEquals(2, clientRepository.getAll().size());
            // TODO: fix warning on .get()
            Client client = clientRepository.getAll().stream().filter(client1 -> Objects.equals(client1.getClientId(), 1L)).findFirst().orElse(null);
            assertNotNull(client);
            assertEquals(client.getClientId(), 1L);
            assertEquals("ntest", client.getName());
            assertEquals("stest", client.getSurname());
            assertEquals("atest", client.getAddress());
            assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
            assertEquals(new BigDecimal(0), client.getBalance());
            client = clientRepository.getAll().stream().filter(client1 -> Objects.equals(client1.getClientId(), 2L)).findFirst().orElse(null);
            assertNotNull(client);
            assertEquals(client.getClientId(), 2L);
            assertEquals("ntest2", client.getName());
            assertEquals("stest2", client.getSurname());
            assertEquals("atest2", client.getAddress());
            assertEquals(LocalDate.of(2002, 1, 1), client.getBirth());
            assertEquals(new BigDecimal(10), client.getBalance());
        }
    }

    @Test
    @Order(6)
    void ClientRepositoryFindClientTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertDoesNotThrow(() -> clientRepository.add(new Client(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0))));
            assertDoesNotThrow(() -> clientRepository.add(new Client(2L, "ntest2", "stest2", "atest2", LocalDate.of(2002, 1, 1), new BigDecimal(0))));
            ArrayList<Client> clientArrayList = clientRepository.find(Filters.eq("balance", BigDecimal.ZERO));
            assertEquals(2, clientArrayList.size());
            Client client = clientArrayList.stream().filter(client1 -> Objects.equals(client1.getClientId(), 1L)).findFirst().orElse(null);
            assertNotNull(client);
            assertEquals(client.getClientId(), 1L);
            assertEquals("ntest", client.getName());
            assertEquals("stest", client.getSurname());
            assertEquals("atest", client.getAddress());
            assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
            assertEquals(new BigDecimal(0), client.getBalance());
            client = clientArrayList.stream().filter(client1 -> Objects.equals(client1.getClientId(), 2L)).findFirst().orElse(null);
            assertNotNull(client);
            assertEquals(client.getClientId(), 2L);
            assertEquals("ntest2", client.getName());
            assertEquals("stest2", client.getSurname());
            assertEquals("atest2", client.getAddress());
            assertEquals(LocalDate.of(2002, 1, 1), client.getBirth());
            assertEquals(new BigDecimal(0), client.getBalance());
            clientArrayList = clientRepository.find(Filters.eq("name", "ntest2"));
            assertEquals(1, clientArrayList.size());
            assertEquals(client.getClientId(), 2L);
            assertEquals("ntest2", client.getName());
            assertEquals("stest2", client.getSurname());
            assertEquals("atest2", client.getAddress());
            assertEquals(LocalDate.of(2002, 1, 1), client.getBirth());
            assertEquals(new BigDecimal(0), client.getBalance());
        }
    }

    /*UPDATE TESTS*/
    @Test
    @Order(7)
    void ClientRepositoryModifyClientSuccessTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            Client client1 = new Client(1L, "Name", "Surname",
                    "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
            assertDoesNotThrow(() -> clientRepository.add(client1));

            clientRepository.updateOne(1L, Updates.set("name", "Name2"));
            clientRepository.updateOne(1L, Updates.set("surname", "Surname2"));
            clientRepository.updateOne(1L, Updates.set("address", "Address2"));
            clientRepository.updateOne(1L, Updates.set("birth", LocalDate.of(2222, 11, 22)));
            clientRepository.updateOne(1L, Updates.set("balance", new BigDecimal(2137)));

            Client client2 = assertDoesNotThrow(() -> clientRepository.get(1L));
            assertNotNull(client2);
            assertEquals(client2.getClientId(), 1L);
            assertEquals("Name2", client2.getName());
            assertEquals("Surname2", client2.getSurname());
            assertEquals("Address2", client2.getAddress(), "Address2");
            assertEquals(LocalDate.of(2222, 11, 22), client2.getBirth());
            assertEquals(new BigDecimal(2137), client2.getBalance());
        }
    }

    @Test
    @Order(8)
    void ClientRepositoryModifyClientFailureTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertDoesNotThrow(() -> clientRepository.add(new Client(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0))));
            assertFalse(clientRepository.updateOne(1L, null));
        }
    }

    /* DELETE TESTS */
    @Test
    @Order(9)
    void ClientRepositoryRemoveClientSuccessTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertDoesNotThrow(() -> clientRepository.add(new Client(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0))));
            assertDoesNotThrow(() -> clientRepository.remove(1L));
        }
    }

    @Test
    @Order(10)
    void ClientRepositoryRemoveClientFailureTest() {
        try (ClientRepository clientRepository = new ClientRepository()) {
            assertThrows(RepositoryException.class, () -> clientRepository.remove(5L));
        }
    }

}
