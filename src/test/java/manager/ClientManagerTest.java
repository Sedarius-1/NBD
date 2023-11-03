//package manager;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import org.ibd.manager.ClientManager;
//import org.ibd.model.clients.Client;
//import org.ibd.repository.ClientRepository;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ClientManagerTest {
//
//    @Test
//    void PersistClientTest() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        clientManager.registerClient(1L, "Name", "Surname",
//                "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
//        Client client = clientManager.getClient(1L);
//        assertNotNull(client);
//        assertEquals(client.getClientId(), 1L);
//        assertEquals(client.getName(), "Name");
//        assertEquals(client.getSurname(), "Surname");
//        assertEquals(client.getAddress(), "Address");
//        assertEquals(client.getBirth(), LocalDate.of(2000, 1, 1));
//        assertEquals(BigDecimal.ZERO, client.getBalance());
//        assertEquals(client.getPurchaseSet().size(), 0);
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerSaveClientFail() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        assertFalse(clientManager.saveClient(null));
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerRegisterClientFail() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        assertFalse(clientManager.registerClient(null, null, null, null, null, null));
//        assertFalse(clientManager.registerClient(1L, null, null, null, null, null));
//        assertFalse(clientManager.registerClient(1L, "jan", null, null, null, null));
//        assertFalse(clientManager.registerClient(1L, "jan", "kowalski", null, null, null));
//        assertFalse(clientManager.registerClient(1L, "jan", "kowalski", "test", null, null));
//        assertFalse(clientManager.registerClient(1L, "jan", "kowalski", "test", LocalDate.now(), null));
//
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerUnregisterClientSuccess() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        clientManager.registerClient(1L, "test", "test", "test", LocalDate.now(), new BigDecimal(0));
//        assertTrue(clientManager.unregisterClient(1L));
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerUnregisterClientFail() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        assertFalse(clientManager.unregisterClient(null));
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerGetClientFail() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        assertNull(clientManager.getClient(null));
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerModifyClientSuccess() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBDunit"); // TODO: change to `test`
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        clientManager.registerClient(1L, "test", "test", "test", LocalDate.now(), new BigDecimal(0));
//        assertTrue(clientManager.changeName(1L, "ntest2"));
//        assertTrue(clientManager.changeSurname(1L, "stest2"));
//        assertTrue(clientManager.changeAddress(1L, "atest2"));
//        assertTrue(clientManager.changeBirth(1L, LocalDate.of(2001, 1, 1)));
//        assertTrue(clientManager.changeBalance(1L, new BigDecimal(200L)));
//        Client client = clientManager.getClient(1L);
//        assertEquals(client.getClientId(), 1L);
//        assertEquals("ntest2", client.getName());
//        assertEquals("stest2", client.getSurname());
//        assertEquals("atest2", client.getAddress());
//        assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
//        assertEquals(new BigDecimal(200L), client.getBalance());
//        assertEquals(0, client.getPurchaseSet().size());
//        entityManagerFactory.close();
//    }
//
//    @Test
//    void ClientManagerModifyClientFailure() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
//        clientManager.registerClient(1L, "ntest", "stest", "atest", LocalDate.of(2001, 1, 1), new BigDecimal(0));
//        assertFalse(clientManager.changeName(1L, null));
//        assertFalse(clientManager.changeSurname(1L, null));
//        assertFalse(clientManager.changeAddress(1L, null));
//        assertFalse(clientManager.changeBirth(1L, null));
//        assertFalse(clientManager.changeBalance(1L, null));
//        Client client = clientManager.getClient(1L);
//        assertEquals(client.getClientId(), 1L);
//        assertEquals("ntest", client.getName());
//        assertEquals("stest", client.getSurname());
//        assertEquals("atest", client.getAddress());
//        assertEquals(LocalDate.of(2001, 1, 1), client.getBirth());
//        assertEquals(new BigDecimal(0), client.getBalance());
//        assertEquals(0, client.getPurchaseSet().size());
//        entityManagerFactory.close();
//    }
//}
