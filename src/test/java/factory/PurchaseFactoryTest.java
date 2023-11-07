package factory;


import org.ibd.factory.PurchaseFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Pistol;
import org.junit.jupiter.api.Test;


import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import static org.junit.jupiter.api.Assertions.*;


public class PurchaseFactoryTest {

    @Test
    void PurchaseFactoryPurchaseCreationTest() {
        Long purchaseId = 1L;
        Client client = mock(Client.class);
        Pistol pistol = mock(Pistol.class);
        Purchase purchase = PurchaseFactory.createPurchase(purchaseId, client, pistol);

        assertNotNull(purchase.getClient());
        assertNotNull(purchase.getWeapon());
        assertEquals(1L, purchase.getPurchaseId());
        assertEquals(client, purchase.getClient());
        assertEquals(pistol, purchase.getWeapon());
    }
}
