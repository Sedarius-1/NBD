package manager;

import org.ibd.manager.ClientManager;
import org.ibd.manager.PurchaseManager;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Pistol;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

        when(client.getClientId()).thenReturn( 1L);
        when(client.getName()).thenReturn( "Name");
        when(client.getSurname()).thenReturn("Surname");
        when(client.getAddress()).thenReturn( "Address");
        when(client.getBirth()).thenReturn( LocalDate.of(2000, 1, 1));
        when(client.getBalance()).thenReturn( new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn( "Pistol");
        when(pistol.getManufacturer()).thenReturn( "Glock");
        when(pistol.getName()).thenReturn( "Glock 17");
        when(pistol.getPrice()).thenReturn( new BigDecimal("2137"));
        when(pistol.getCaliber()).thenReturn( "9mm");

        assertTrue(purchaseManager.registerPurchase(1L, client, pistol));

    }

    @Test
    @Order(2)
    void PurchaseManagerRegisterPurchaseFailureTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn( 1L);
        when(client.getName()).thenReturn( "Name");
        when(client.getSurname()).thenReturn("Surname");
        when(client.getAddress()).thenReturn( "Address");
        when(client.getBirth()).thenReturn( LocalDate.of(2000, 1, 1));
        when(client.getBalance()).thenReturn( BigDecimal.ZERO);

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn( "Pistol");
        when(pistol.getManufacturer()).thenReturn( "Glock");
        when(pistol.getName()).thenReturn( "Glock 17");
        when(pistol.getPrice()).thenReturn( new BigDecimal("2137"));
        when(pistol.getCaliber()).thenReturn( "9mm");

        assertFalse(purchaseManager.registerPurchase(1L, client, pistol));

    }

    @Test
    @Order(3)
    void PurchaseManagerGetPurchaseSuccessTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        Client client = spy(Client.class);

        when(client.getClientId()).thenReturn( 1L);
        when(client.getBalance()).thenReturn( new BigDecimal("2138"));

        Pistol pistol = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn( "Pistol");
        when(pistol.getPrice()).thenReturn( new BigDecimal("2137"));

        purchaseManager.registerPurchase(1L, client, pistol);
        assertNotNull(purchaseManager.getPurchase(1L));
    }

    @Test
    @Order(4)
    void PurchaseManagerGetPurchaseFailureTest() {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        assertNull(purchaseManager.getPurchase(1L));
    }
}
