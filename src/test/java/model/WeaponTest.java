package model;


import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Rifle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {
    @Test
    void PistolTest() {
        Pistol pistol = new Pistol(2L, "Glock", "Glock 17", new BigDecimal("2137"), "9mm");
        assertNotNull(pistol);
        assertEquals(pistol.getSerialNumber(), 2L);
        assertEquals(pistol.getType(), "Pistol");
        assertEquals(pistol.getManufacturer(), "Glock");
        assertEquals(pistol.getName(), "Glock 17");
        assertEquals(pistol.getPrice(), new BigDecimal("2137"));
        assertEquals(pistol.getCaliber(), "9mm");

        pistol.setManufacturer("a");
        pistol.setName("aa");
        pistol.setPrice(new BigDecimal("0"));
        pistol.setCaliber("aaa");

        assertEquals(pistol.getManufacturer(), "a");
        assertEquals(pistol.getName(), "aa");
        assertEquals(pistol.getPrice(), new BigDecimal("0"));
        assertEquals(pistol.getCaliber(), "aaa");
    }

    @Test
    void RifleTest() {
        Rifle rifle = new Rifle(1L, "Ruger", "PPC", new BigDecimal("4000"), "22LR", Float.valueOf("15.6"));
        assertNotNull(rifle);
        assertEquals(rifle.getSerialNumber(), 1L);
        assertEquals(rifle.getType(), "Rifle");
        assertEquals(rifle.getManufacturer(), "Ruger");
        assertEquals(rifle.getName(), "PPC");
        assertEquals(rifle.getPrice(), new BigDecimal("4000"));
        assertEquals(rifle.getCaliber(), "22LR");
        assertEquals(rifle.getLength(), Float.valueOf("15.6"));

        rifle.setManufacturer("a");
        rifle.setName("aa");
        rifle.setPrice(new BigDecimal("0"));
        rifle.setCaliber("aaa");
        rifle.setLength(Float.valueOf("1"));

        assertEquals(rifle.getManufacturer(), "a");
        assertEquals(rifle.getName(), "aa");
        assertEquals(rifle.getPrice(), new BigDecimal("0"));
        assertEquals(rifle.getCaliber(), "aaa");
        assertEquals(rifle.getLength(), Float.valueOf("1"));
    }

}
