//package model;
//
//import org.ibd.model.clients.Client;
//import org.ibd.model.purchases.Purchase;
//import org.ibd.model.weapons.Pistol;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//
//public class PurchaseTest {
//
//    @Test
//    void PurchaseAttrTest() {
//
//        Long purchaseId = 1L;
//        Client client = mock(Client.class);
//        Pistol pistol = mock(Pistol.class);
//
//        Purchase purchase = new Purchase(purchaseId, client, pistol);
//
//        assertNull(purchase.getId());
//        assertNotNull(purchase.getClient());
//        assertNotNull(purchase.getWeapon());
//        assertEquals(1L, purchase.getPurchaseId());
//        assertEquals(client, purchase.getClient());
//        assertEquals(pistol, purchase.getWeapon());
//
//        Long purchaseIdDifferent = 2L;
//        Client clientDifferent = mock(Client.class);
//        Pistol pistolDifferent = mock(Pistol.class);
//
//        purchase.setPurchaseId(purchaseIdDifferent);
//        purchase.setClient(clientDifferent);
//        purchase.setWeapon(pistolDifferent);
//
//        assertEquals(purchaseIdDifferent, purchase.getPurchaseId());
//        assertEquals(clientDifferent, purchase.getClient());
//        assertEquals(pistolDifferent, purchase.getWeapon());
//    }
//}
