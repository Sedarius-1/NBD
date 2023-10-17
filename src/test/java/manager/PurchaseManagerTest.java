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
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Rifle;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseManagerTest {
    private Map<String, String> pistolMap;

    @BeforeEach
    void prepareMap() {
        pistolMap = new HashMap<>();
        pistolMap.put("serialNumber", "1");
        pistolMap.put("manufacturer", "Glock");
        pistolMap.put("name", "Glock 19");
        pistolMap.put("price", "2500");
        pistolMap.put("caliber", "9mm");
    }

    @Test
    void PersistPurchaseTest() throws RepositoryException {
        // RUN DOCKER COMPOSE BEFORE TESTS!!!
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBDunit"); // TODO: change to `test`
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository(entityManager));
        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
        Client client = ClientFactory.createClient(1024L, "test", "Test", "test", LocalDate.now(), new BigDecimal("0"));

        Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, pistolMap);
        clientManager.saveClient(client);
        client = clientManager.getClient(client.getClientId());
        purchaseManager.registerPurchase(1024L, client, pistol);

        assertDoesNotThrow(purchaseManager::getAllPurchases);
        assertEquals(1, purchaseManager.getAllPurchases().size());
        Purchase purchase = purchaseManager.getPurchase(1024L);
        assertEquals(purchase.getPurchaseId(), 1024L);
        assertEquals(purchase.getWeapon(), pistol);
        assertEquals(purchase.getClient(), client);
        entityManagerFactory.close();
    }

    @Test
    void PersistPurchaseUndoTest() throws RepositoryException {
        // RUN DOCKER COMPOSE BEFORE TESTS!!!
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBDunit"); // TODO: change to `test`
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository(entityManager));
        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
        Client client = ClientFactory.createClient(1024L, "test", "Test", "test", LocalDate.now(), new BigDecimal("0"));

        Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, pistolMap);
        clientManager.saveClient(client);
        client = clientManager.getClient(client.getClientId());
        purchaseManager.registerPurchase(1024L, client, pistol);

        Purchase purchase = purchaseManager.getPurchase(1024L);
        assertTrue(purchaseManager.undoPurchase(purchase));

        assertNull(purchaseManager.getPurchase(1024L));
        assertEquals(0, purchaseManager.getAllPurchases().size());
        entityManagerFactory.close();
    }

    @Test
    void CalculateTotalTest() throws RepositoryException {
        // RUN DOCKER COMPOSE BEFORE TESTS!!!
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBDunit"); // TODO: change to `test`
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository(entityManager));

        ClientManager clientManager = new ClientManager(new ClientRepository(entityManager));
        Client client = ClientFactory.createClient(1024L, "test", "Test", "test", LocalDate.now(), new BigDecimal("4000"));
        clientManager.saveClient(client);
        client = clientManager.getClient(client.getClientId());

        Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, pistolMap);
        Map<String, String> rifleMap = new HashMap<>();
        rifleMap.put("serialNumber", "1");
        rifleMap.put("manufacturer", "Glock");
        rifleMap.put("name", "Glock 19");
        rifleMap.put("price", "1000");
        rifleMap.put("caliber", "9mm");
        rifleMap.put("length", "15.6");

        Rifle rifle = WeaponFactory.manufactureWeapon(WeaponTypeEnum.RIFLE, rifleMap);

        purchaseManager.registerPurchase(1024L, client, pistol);
        purchaseManager.registerPurchase(1024L, client, rifle);

        assertEquals(purchaseManager.calculateTotalSumOfClientPurchases(client), new BigDecimal(3500));
        entityManagerFactory.close();
    }

}
