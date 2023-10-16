package manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.exceptions.RepositoryException;
import org.ibd.manager.PurchaseManager;
import org.ibd.manager.WeaponManager;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Pistol;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

public class PurchaseManagerTest {

    @Test
    void PersistPurchaseTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository(entityManager));
        Client client = spy(Client.class);
        client.setPurchaseSet(new HashSet<>());
        Pistol pistol = spy(Pistol.class);

        purchaseManager.registerPurchase(1L, client, pistol);

        assertDoesNotThrow(purchaseManager::getAllPurchases);
        assertEquals(purchaseManager.getAllPurchases().size(), 1); // Fuk
        Purchase purchase = purchaseManager.getPurchase(1L);
        assertEquals(purchase.getPurchaseId(), 1L);
        assertEquals(purchase.getWeapon(), pistol);
        assertEquals(purchase.getClient(), client);

        entityManagerFactory.close();
    }

    // TODO: add bad case tests
}
