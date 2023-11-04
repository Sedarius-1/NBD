package manager;


import com.mongodb.client.model.Filters;
import org.ibd.manager.ClientManager;
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
public class ClientManagerTest {

    /* CREATE TESTS */
    @Test
    @Order(1)
    void ClientManagerRegisterClientSuccessTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        assertTrue(clientManager.registerClient(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO));

    }

    @Test
    @Order(2)
    void ClientManagerRegisterClientFailTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        assertFalse(clientManager.registerClient(null, null, null, null, null, null));
        assertFalse(clientManager.registerClient(1L, null, null, null, null, null));
        assertFalse(clientManager.registerClient(1L, "jan", null, null, null, null));
        assertFalse(clientManager.registerClient(1L, "jan", "kowalski", null, null, null));
        assertFalse(clientManager.registerClient(1L, "jan", "kowalski", "test", null, null));
        assertFalse(clientManager.registerClient(1L, "jan", "kowalski", "test", LocalDate.now(), null));
    }

    /* READ TESTS */

    @Test
    @Order(3)
    void ClientManagerGetClientSuccessTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        assertNull(clientManager.getClient(1L));
    }

    @Test
    @Order(4)
    void ClientManagerGetClientFailTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        assertNull(clientManager.getClient(1L));
    }

    @Test
    @Order(5)
    void ClientManagerGetAllClientsTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        assertEquals(0, clientManager.getAllClients().size());
        clientManager.registerClient(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0));
        clientManager.registerClient(2L, "ntest2", "stest2", "atest2", LocalDate.of(2002, 1, 1), new BigDecimal(10));
        assertEquals(2, clientManager.getAllClients().size());
        // TODO: fix warning on .get()
        Client client = clientManager.getAllClients().stream().filter(client1 -> Objects.equals(client1.getClientId(), 1L)).findFirst().get();
        assertEquals(client.getClientId(), 1L);
        assertEquals("ntest", client.getName());
        assertEquals("stest", client.getSurname());
        assertEquals("atest", client.getAddress());
        assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(0), client.getBalance());
        client = clientManager.getAllClients().stream().filter(client1 -> Objects.equals(client1.getClientId(), 2L)).findFirst().get();
        assertEquals(client.getClientId(), 2L);
        assertEquals("ntest2", client.getName());
        assertEquals("stest2", client.getSurname());
        assertEquals("atest2", client.getAddress());
        assertEquals(LocalDate.of(2002, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(10), client.getBalance());
    }

    @Test
    @Order(6)
    void ClientManagerFindClientsTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        clientManager.registerClient(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0));
        clientManager.registerClient(2L, "ntest2", "stest2", "atest2", LocalDate.of(2002, 1, 1), new BigDecimal(0));
        ArrayList<Client> clientArrayList = clientManager.findClients(Filters.eq("balance", BigDecimal.ZERO));
        assertEquals(2, clientArrayList.size());
        Client client = clientArrayList.stream().filter(client1 -> Objects.equals(client1.getClientId(), 1L)).findFirst().get();
        assertEquals(client.getClientId(), 1L);
        assertEquals("ntest", client.getName());
        assertEquals("stest", client.getSurname());
        assertEquals("atest", client.getAddress());
        assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(0), client.getBalance());
        client = clientArrayList.stream().filter(client1 -> Objects.equals(client1.getClientId(), 2L)).findFirst().get();
        assertEquals(client.getClientId(), 2L);
        assertEquals("ntest2", client.getName());
        assertEquals("stest2", client.getSurname());
        assertEquals("atest2", client.getAddress());
        assertEquals(LocalDate.of(2002, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(0), client.getBalance());
        clientArrayList = clientManager.findClients(Filters.eq("name", "ntest2"));
        assertEquals(1, clientArrayList.size());
        assertEquals(client.getClientId(), 2L);
        assertEquals("ntest2", client.getName());
        assertEquals("stest2", client.getSurname());
        assertEquals("atest2", client.getAddress());
        assertEquals(LocalDate.of(2002, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(0), client.getBalance());
    }

    /* UPDATE TESTS */

    @Test
    @Order(7)
    void ClientManagerModifyClientSuccessTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        clientManager.registerClient(1L, "test", "test", "test", LocalDate.now(), new BigDecimal(0));
        assertTrue(clientManager.changeName(1L, "ntest2"));
        assertTrue(clientManager.changeSurname(1L, "stest2"));
        assertTrue(clientManager.changeAddress(1L, "atest2"));
        assertTrue(clientManager.changeBirth(1L, LocalDate.of(2001, 1, 1)));
        assertTrue(clientManager.changeBalance(1L, new BigDecimal(200L)));
        Client client = clientManager.getClient(1L);
        assertEquals(client.getClientId(), 1L);
        assertEquals("ntest2", client.getName());
        assertEquals("stest2", client.getSurname());
        assertEquals("atest2", client.getAddress());
        assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(200L), client.getBalance());
    }

    @Test
    @Order(8)
    void ClientManagerModifyClientFailureTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        clientManager.registerClient(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0));
        assertFalse(clientManager.changeName(1L, null));
        assertFalse(clientManager.changeSurname(1L, null));
        assertFalse(clientManager.changeAddress(1L, null));
        assertFalse(clientManager.changeBirth(1L, null));
        assertFalse(clientManager.changeBalance(1L, null));
        Client client = clientManager.getClient(1L);
        assertEquals(client.getClientId(), 1L);
        assertEquals("ntest", client.getName());
        assertEquals("stest", client.getSurname());
        assertEquals("atest", client.getAddress());
        assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
        assertEquals(new BigDecimal(0), client.getBalance());
    }

    /* DELETE TESTS */

    @Test
    @Order(9)
    void ClientManagerUnregisterClientSuccessTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        clientManager.registerClient(1L, "test", "test", "test", LocalDate.now(), new BigDecimal(0));
        assertTrue(clientManager.unregisterClient(1L));
    }

    @Test
    @Order(10)
    void ClientManagerUnregisterClientFailureTest() {
        ClientManager clientManager = new ClientManager(new ClientRepository());
        assertFalse(clientManager.unregisterClient(1L));
    }


}
