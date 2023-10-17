package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Pistol;
import org.ibd.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class PurchaseRepositoryTest {
    @Test
    void PurchaseRepositoryCreatePurchaseTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseRepository purchaseRepository = new PurchaseRepository(entityManager);
        Client client = spy(Client.class);
        client.setPurchaseSet(new HashSet<>());
        Pistol pistol = mock(Pistol.class);
        assertDoesNotThrow(() -> purchaseRepository.add(new Purchase(1L, client, pistol)));

        Purchase purchase = purchaseRepository.get(1L);
        assertNotNull(purchase);
        assertEquals(purchase.getPurchaseId(), 1L);
        assertEquals(purchase.getClient(), client);
        assertEquals(purchase.getWeapon(), pistol);

        purchaseRepository.remove(purchase);

        assertThrows(RepositoryException.class, () -> purchaseRepository.get(1L));

        entityManagerFactory.close();
    }

    @Test
    void PurchaseRepositoryAddFailureTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseRepository purchaseRepository = new PurchaseRepository(entityManager);
        assertThrows(RepositoryException.class, () -> purchaseRepository.add(null));
        entityManagerFactory.close();
    }

    @Test
    void PurchaseRepositoryRemoveFailureTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseRepository purchaseRepository = new PurchaseRepository(entityManager);
        assertThrows(RepositoryException.class, () -> purchaseRepository.remove(null));
        entityManagerFactory.close();
    }

    @Test
    void PurchaseRepositoryGetAllPurchasesTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseRepository purchaseRepository = new PurchaseRepository(entityManager);
        assertDoesNotThrow(purchaseRepository::getAllPurchases);
        entityManagerFactory.close();
    }

    @Test
    void PurchaseRepositoryGetAllPurchasesForClientTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseRepository purchaseRepository = new PurchaseRepository(entityManager);

        Client client = ClientFactory.createClient(1L, "test", "test", "test", LocalDate.now(), new BigDecimal(0));
        Pistol pistol = mock(Pistol.class);
        assertDoesNotThrow(() -> purchaseRepository.add(new Purchase(1L, client, pistol)));
        assertDoesNotThrow(() -> purchaseRepository.getAllPurchasesForSingleClient(1L));
        assertEquals(1, purchaseRepository.getAllPurchasesForSingleClient(1L).size());
        entityManagerFactory.close();
    }
}
