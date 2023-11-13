package repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.purchases.PurchaseMap;
import org.ibd.model.purchases.PurchaseMapper;
import org.ibd.model.weapons.Pistol;
import org.ibd.repository.PurchaseRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PurchaseRepositoryTest {

    /* CREATE TESTS */

    @Test
    @Order(1)
    void PurchaseRepositoryCreatePurchaseSuccessTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
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

            PurchaseMap purchaseMap = new PurchaseMap();
            PurchaseMapper.convertPurchaseToPurchaseMap(purchaseMap, new Purchase(1L, client, pistol));
            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
        }
    }

    @Test
    @Order(2)
    void  PurchaseRepositoryCreatePurchaseFailureTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            assertThrows(RepositoryException.class, () -> purchaseRepository.add(null));
        }
    }

    /* READ TESTS*/
    @Test
    @Order(3)
    void PurchaseRepositoryGetPurchaseSuccessTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            PurchaseMap purchaseMap = spy(PurchaseMap.class);
            when(purchaseMap.getPurchaseId()).thenReturn(1L);
            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
            assertDoesNotThrow(() -> purchaseRepository.get(1L));

        }
    }

    @Test
    @Order(4)
    void PurchaseRepositoryGetPurchaseFailureTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            assertThrows(RepositoryException.class, () -> purchaseRepository.get(1L));


        }
    }

    @Test
    @Order(5)
    void PurchaseRepositoryGetAllPurchaseTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            PurchaseMap purchaseMap = spy(PurchaseMap.class);
            when(purchaseMap.getPurchaseId()).thenReturn(1L);

            PurchaseMap purchaseMap1 = spy(PurchaseMap.class);
            when(purchaseMap1.getPurchaseId()).thenReturn(2L);

            assertEquals(0, purchaseRepository.getAll().size());
            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap1));
            assertEquals(2, purchaseRepository.getAll().size());
            PurchaseMap purchaseMap2 = purchaseRepository.getAll().stream().filter(purchaseMapLambda -> Objects.equals(purchaseMapLambda.getPurchaseId(), 1L)).findFirst().orElse(null);
            assertNotNull(purchaseMap2);
            PurchaseMap purchaseMap3 = purchaseRepository.getAll().stream().filter(purchaseMapLambda -> Objects.equals(purchaseMapLambda.getPurchaseId(), 1L)).findFirst().orElse(null);
            assertNotNull(purchaseMap3);
        }
    }

    @Test
    @Order(6)
    void PurchaseRepositoryFindPurchaseTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {

            PurchaseMap purchaseMap = spy(PurchaseMap.class);
            when(purchaseMap.getPurchaseId()).thenReturn(1L);
            when(purchaseMap.getClientId()).thenReturn(1L);
            when(purchaseMap.getType()).thenReturn("Pistol");

            PurchaseMap purchaseMap1 = spy(PurchaseMap.class);
            when(purchaseMap1.getPurchaseId()).thenReturn(2L);
            when(purchaseMap1.getClientId()).thenReturn(1L);
            when(purchaseMap1.getType()).thenReturn("Rifle");

            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap1));
            ArrayList<PurchaseMap> purchaseMapArrayList = purchaseRepository.find(Filters.eq("clientId",1L));
            assertEquals(2, purchaseMapArrayList.size());
            purchaseMapArrayList = purchaseRepository.find(Filters.eq("type","Pistol"));
            assertEquals(1, purchaseMapArrayList.size());
            purchaseMapArrayList = purchaseRepository.find(Filters.eq("type","Rifle"));
            assertEquals(1, purchaseMapArrayList.size());
            purchaseMapArrayList = purchaseRepository.find(Filters.eq("surname","Nothing"));
            assertEquals(0, purchaseMapArrayList.size());

        }
    }
    /*UPDATE TESTS*/

    @Test
    @Order(7)
    void PurchaseRepositoryModifyPurchaseSuccessTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            PurchaseMap purchaseMap = spy(PurchaseMap.class);
            when(purchaseMap.getPurchaseId()).thenReturn(1L);
            when(purchaseMap.getClientId()).thenReturn(1L);

            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
            assertTrue(purchaseRepository.updateOne(1L, Updates.set("type", "Pistol")));
            PurchaseMap purchaseMap1 = assertDoesNotThrow(() -> purchaseRepository.get(1L));
            assertNotNull(purchaseMap1);
            assertEquals(1L, purchaseMap1.getClientId());
            assertEquals("Pistol",  purchaseMap1.getType());
        }
    }

    @Test
    @Order(8)
    void PurchaseRepositoryModifyPurchaseFailureTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            PurchaseMap purchaseMap = spy(PurchaseMap.class);
            when(purchaseMap.getPurchaseId()).thenReturn(1L);
            when(purchaseMap.getClientId()).thenReturn(1L);

            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
            assertFalse(purchaseRepository.updateOne(1L, null));
        }
    }

    /* DELETE TESTS */
    @Test
    @Order(9)
    void PurchaseRepositoryRemovePurchaseSuccessTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            PurchaseMap purchaseMap = spy(PurchaseMap.class);
            when(purchaseMap.getPurchaseId()).thenReturn(1L);
            when(purchaseMap.getClientId()).thenReturn(1L);

            assertDoesNotThrow(() -> purchaseRepository.add(purchaseMap));
            assertDoesNotThrow(() -> purchaseRepository.remove(1L));
        }
    }

    @Test
    @Order(10)
    void PurchaseRepositoryRemovePurchaseFailureTest() {
        try (PurchaseRepository purchaseRepository = new PurchaseRepository()) {
            assertThrows(RepositoryException.class, () -> purchaseRepository.remove(5L));
        }
    }
}
