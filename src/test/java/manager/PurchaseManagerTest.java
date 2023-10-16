package manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.factory.WeaponFactory;
import org.ibd.manager.ClientManager;
import org.ibd.manager.PurchaseManager;
import org.ibd.manager.WeaponManager;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Pistol;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

public class PurchaseManagerTest {

    @Test
    void DummyTest(){
        assertTrue(true);
    }
    @Test
    void PersistPurchaseTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository(entityManager));
        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
        Client client = ClientFactory.createClient(1024L, "test", "Test", "test", LocalDate.now(), new BigDecimal("0"));
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "1");
        map.put("manufacturer", "Glock");
        map.put("name", "Glock 19");
        map.put("price", "2500");
        map.put("caliber", "9mm");
        Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
        clientManager.saveClient(client);
        client = clientManager.getClient(client.getClientId());
        purchaseManager.registerPurchase(1024L, client, pistol);

        assertDoesNotThrow(purchaseManager::getAllPurchases);
        assertEquals(1, purchaseManager.getAllPurchases().size()); // Fuk
        Purchase purchase = purchaseManager.getPurchase(1024L);
        assertEquals(purchase.getPurchaseId(), 1024L);
        assertEquals(purchase.getWeapon(), pistol);
        assertEquals(purchase.getClient(), client);
        purchaseManager.undoPurchase(purchase);
        entityManagerFactory.close();
    }

    // TODO: add bad case tests
}
