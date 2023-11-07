package model;

import org.ibd.model.clients.Client;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.purchases.PurchaseMap;
import org.ibd.model.purchases.PurchaseMapper;
import org.ibd.model.weapons.HandGrenade;
import org.ibd.model.weapons.Pistol;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PurchaseMapTest {

    @Test
    void PurchaseToPurchaseMapConversion() {
        Pistol pistol  = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn( "Pistol");
        when(pistol.getManufacturer()).thenReturn( "Glock");
        when(pistol.getName()).thenReturn( "Glock 17");
        when(pistol.getPrice()).thenReturn( new BigDecimal("2137"));
        when(pistol.getCaliber()).thenReturn( "9mm");

        Client client  = spy(Client.class);

        when(client.getClientId()).thenReturn( 1L);
        when(client.getName()).thenReturn( "Name");
        when(client.getSurname()).thenReturn("Surname");
        when(client.getAddress()).thenReturn( "Address");
        when(client.getBirth()).thenReturn( LocalDate.of(2000, 1, 1));
        when(client.getBalance()).thenReturn( BigDecimal.ZERO);

        Purchase purchase = new Purchase(1L, client, pistol);

        PurchaseMap purchaseMap = new PurchaseMap();

        PurchaseMapper.convertPurchaseToPurchaseMap(purchaseMap, purchase);

        assertEquals(1L, purchaseMap.getPurchaseId());
        assertEquals(client.getName(), purchaseMap.getName());
        assertEquals(client.getSurname(), purchaseMap.getSurname());
        assertEquals(client.getAddress(), purchaseMap.getAddress());
        assertEquals(client.getBirth(), purchaseMap.getBirth());
        assertEquals(client.getBalance(), purchaseMap.getBalance());
        assertEquals(pistol.getSerialNumber(), purchaseMap.getSerialNumber());
        assertEquals(pistol.getType(), purchaseMap.getType());
        assertEquals(pistol.getManufacturer(), purchaseMap.getManufacturer());assertEquals(1L, purchaseMap.getPurchaseId());
        assertEquals(pistol.getName(), purchaseMap.getWeaponName());
        assertEquals(pistol.getPrice(), purchaseMap.getPrice());
        assertEquals(pistol.getCaliber(), purchaseMap.getCaliber());
    }

    @Test
    void PurchaseMapToPurchaseConversion() {
        PurchaseMap purchaseMap = spy(PurchaseMap.class);
        when(purchaseMap.getPurchaseId()).thenReturn( 1L);
        when(purchaseMap.getClientId()).thenReturn( 1L);
        when(purchaseMap.getName()).thenReturn( "Name");
        when(purchaseMap.getSurname()).thenReturn("Surname");
        when(purchaseMap.getAddress()).thenReturn( "Address");
        when(purchaseMap.getBirth()).thenReturn( LocalDate.of(2000, 1, 1));
        when(purchaseMap.getBalance()).thenReturn( BigDecimal.ZERO);
        when(purchaseMap.getSerialNumber()).thenReturn(2L);
        when(purchaseMap.getType()).thenReturn( "Pistol");
        when(purchaseMap.getManufacturer()).thenReturn( "Glock");
        when(purchaseMap.getWeaponName()).thenReturn( "Glock 17");
        when(purchaseMap.getPrice()).thenReturn( new BigDecimal("2137"));
        when(purchaseMap.getCaliber()).thenReturn( "9mm");

        Purchase purchase = PurchaseMapper.convertPurchaseMapToPurchase(purchaseMap);
        assertEquals(purchaseMap.getPurchaseId()    ,purchase.getPurchaseId());
        assertEquals(purchaseMap.getClientId()      ,purchase.getClient().getClientId());
        assertEquals(purchaseMap.getName()          ,purchase.getClient().getName());
        assertEquals(purchaseMap.getSurname()       ,purchase.getClient().getSurname());
        assertEquals(purchaseMap.getAddress()       ,purchase.getClient().getAddress());
        assertEquals(purchaseMap.getBirth()         ,purchase.getClient().getBirth());
        assertEquals(purchaseMap.getBalance()       ,purchase.getClient().getBalance());
        assertEquals(purchaseMap.getSerialNumber()  ,purchase.getWeapon().getSerialNumber());
        assertEquals(purchaseMap.getType()          ,purchase.getWeapon().getType());
        assertEquals(purchaseMap.getManufacturer()  ,purchase.getWeapon().getManufacturer());
        assertEquals(purchaseMap.getWeaponName()    ,purchase.getWeapon().getName());
        assertEquals(purchaseMap.getPrice()         ,purchase.getWeapon().getPrice());

        Pistol pistol = (Pistol) purchase.getWeapon();

        assertEquals(purchaseMap.getCaliber()         ,pistol.getCaliber());

    }
}
