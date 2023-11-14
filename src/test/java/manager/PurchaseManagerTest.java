package manager;

import com.mongodb.client.model.Filters;
import org.ibd.exceptions.RepositoryException;
import org.ibd.manager.ClientManager;
import org.ibd.manager.PurchaseManager;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.purchases.PurchaseMap;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Rifle;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PurchaseManagerTest {

    /* CREATE TESTS */
    @Test
    @Order(1)
    void PurchaseManagerRegisterPurchaseSuccessTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn(1L);
        when(client.getName()).thenReturn("Name");
        when(client.getSurname()).thenReturn("Surname");
        when(client.getAddress()).thenReturn("Address");
        when(client.getBirth()).thenReturn(LocalDate.of(2000, 1, 1));
        when(client.getBalance()).thenReturn(new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn("Pistol");
        when(pistol.getManufacturer()).thenReturn("Glock");
        when(pistol.getName()).thenReturn("Glock 17");
        when(pistol.getPrice()).thenReturn(new BigDecimal("2137"));
        when(pistol.getCaliber()).thenReturn("9mm");

        assertTrue(purchaseManager.registerPurchase(1L, client, pistol));

    }

    @Test
    @Order(2)
    void PurchaseManagerRegisterPurchaseFailureTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn(1L);
        when(client.getName()).thenReturn("Name");
        when(client.getSurname()).thenReturn("Surname");
        when(client.getAddress()).thenReturn("Address");
        when(client.getBirth()).thenReturn(LocalDate.of(2000, 1, 1));
        when(client.getBalance()).thenReturn(BigDecimal.ZERO);

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn("Pistol");
        when(pistol.getManufacturer()).thenReturn("Glock");
        when(pistol.getName()).thenReturn("Glock 17");
        when(pistol.getPrice()).thenReturn(new BigDecimal("2137"));
        when(pistol.getCaliber()).thenReturn("9mm");

        assertFalse(purchaseManager.registerPurchase(1L, client, pistol));

    }

    @Test
    @Order(3)
    void PurchaseManagerGetPurchaseSuccessTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn(1L);
        when(client.getBalance()).thenReturn(new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn("Pistol");
        when(pistol.getPrice()).thenReturn(new BigDecimal("2137"));

        purchaseManager.registerPurchase(1L, client, pistol);
        assertNotNull(purchaseManager.getPurchase(1L));
    }

    @Test
    @Order(4)
    void PurchaseManagerGetPurchaseFailureTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        assertNull(purchaseManager.getPurchase(1L));
    }

    @Test
    @Order(5)
    void PurchaseManagerGetAllPurchaseTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());

        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn(1L);
        when(client.getBalance()).thenReturn(new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn("Pistol");
        when(pistol.getPrice()).thenReturn(new BigDecimal("2137"));

        Rifle rifle = spy(Rifle.class);

        when(rifle.getSerialNumber()).thenReturn(3L);
        when(rifle.getType()).thenReturn("Rifle");
        when(rifle.getPrice()).thenReturn(new BigDecimal("1"));


        assertEquals(0, purchaseManager.getAllPurchases().size());
        assertDoesNotThrow(() -> purchaseManager.registerPurchase(1L, client, pistol));
        assertDoesNotThrow(() -> purchaseManager.registerPurchase(2L, client, rifle));
        assertEquals(2, purchaseManager.getAllPurchases().size());
        Purchase purchase = purchaseManager.getAllPurchases().stream().filter(purchaseMapLambda -> Objects.equals(purchaseMapLambda.getPurchaseId(), 1L)).findFirst().orElse(null);
        assertNotNull(purchase);
        Purchase purchase2 = purchaseManager.getAllPurchases().stream().filter(purchaseMapLambda -> Objects.equals(purchaseMapLambda.getPurchaseId(), 1L)).findFirst().orElse(null);
        assertNotNull(purchase2);

    }

    @Test
    @Order(6)
    void PurchaseManagerFindPurchaseTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());

        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn(1L);
        when(client.getBalance()).thenReturn(new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn("Pistol");
        when(pistol.getPrice()).thenReturn(new BigDecimal("2137"));

        Rifle rifle = spy(Rifle.class);

        when(rifle.getSerialNumber()).thenReturn(3L);
        when(rifle.getType()).thenReturn("Rifle");
        when(rifle.getPrice()).thenReturn(new BigDecimal("1"));

        assertDoesNotThrow(() -> purchaseManager.registerPurchase(1L, client, pistol));
        assertDoesNotThrow(() -> purchaseManager.registerPurchase(2L, client, rifle));
        ArrayList<Purchase> purchaseArrayList = purchaseManager.findPurchases(Filters.eq("clientId", 1L));
        assertEquals(2, purchaseArrayList.size());
        purchaseArrayList = purchaseManager.findPurchases(Filters.eq("type", "Pistol"));
        assertEquals(1, purchaseArrayList.size());
        purchaseArrayList = purchaseManager.findPurchases(Filters.eq("type", "Rifle"));
        assertEquals(1, purchaseArrayList.size());
        purchaseArrayList = purchaseManager.findPurchases(Filters.eq("surname", "Nothing"));
        assertEquals(0, purchaseArrayList.size());

    }

    /* DELETE TESTS */

    @Test
    @Order(7)
    void PurchaseManagerRemovePurchaseSuccessTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());

        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn(1L);
        when(client.getBalance()).thenReturn(new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn("Pistol");
        when(pistol.getPrice()).thenReturn(new BigDecimal("2137"));

        assertDoesNotThrow(() -> purchaseManager.registerPurchase(1L, client, pistol));
        assertDoesNotThrow(() -> purchaseManager.unregisterPurchase(1L));
    }

    @Test
    @Order(8)
    void PurchaseManagerRemovePurchaseFailureTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        assertFalse(purchaseManager.unregisterPurchase(5L));
    }

}
