//package factory;
//
//
//import org.ibd.factory.PurchaseFactory;
//import org.ibd.model.clients.Client;
//import org.ibd.model.weapons.Pistol;
//import org.junit.jupiter.api.Test;
//
//
//import java.util.HashSet;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.spy;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class PurchaseFactoryTest {
//
//    @Test
//    void PurchaseFactoryPurchaseCreationTest() {
//        Long purchaseId = 1L;
//        Client client = spy(Client.class);
//        client.setPurchaseSet(new HashSet<>());
//        Pistol pistol = mock(Pistol.class);
//        PurchaseFactory.createPurchase(purchaseId, client, pistol);
//        assertEquals(1, client.getPurchaseSet().size());
//    }
//}
