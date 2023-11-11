package model;

import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.WeaponMap;
import org.ibd.model.weapons.WeaponMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class WeaponMapTest {

    @Test
    public void WeaponMapCorrectWeaponTypeTest(){
        WeaponMap weaponMap = new WeaponMap();
        Pistol pistol  = spy(Pistol.class);

        when(pistol.getSerialNumber()).thenReturn(2L);
        when(pistol.getType()).thenReturn( "Pistol");
        when(pistol.getManufacturer()).thenReturn( "Glock");
        when(pistol.getName()).thenReturn( "Glock 17");
        when(pistol.getPrice()).thenReturn( new BigDecimal("2137"));
        when(pistol.getCaliber()).thenReturn( "9mm");

        WeaponMapper.convertWeaponToWeaponMap(weaponMap, pistol);
        
        Pistol pistol1 = (Pistol) WeaponMapper.convertWeaponMapToWeapon(weaponMap);
        assertInstanceOf(Pistol.class, pistol1);
        assertEquals(pistol.getSerialNumber(), weaponMap.getSerialNumber());
        assertEquals(pistol.getType(), weaponMap.getType());
        assertEquals(pistol.getManufacturer(), weaponMap.getManufacturer());
        assertEquals(pistol.getName(), weaponMap.getName());
        assertEquals(pistol.getPrice(), weaponMap.getPrice());
        assertEquals(pistol.getCaliber(), weaponMap.getCaliber());
    }
  
}
