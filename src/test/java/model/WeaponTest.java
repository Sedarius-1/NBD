//package model;
//
//import org.ibd.model.enums.GrenadeType;
//import org.ibd.model.weapons.HandGrenade;
//import org.ibd.model.weapons.Pistol;
//import org.ibd.model.weapons.RecreationalMcNuke;
//import org.ibd.model.weapons.Rifle;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WeaponTest {
//    @Test
//    void PistolTest() {
//        Pistol pistol = new Pistol(2L, "Glock", "Glock 17", new BigDecimal("2137"), "9mm");
//        assertNotNull(pistol);
//        assertNull(pistol.getId());
//        assertEquals(pistol.getSerialNumber(), 2L);
//        assertEquals(pistol.getType(), "Pistol");
//        assertEquals(pistol.getManufacturer(), "Glock");
//        assertEquals(pistol.getName(), "Glock 17");
//        assertEquals(pistol.getPrice(), new BigDecimal("2137"));
//        assertEquals(pistol.getCaliber(), "9mm");
//
//        pistol.setManufacturer("a");
//        pistol.setName("aa");
//        pistol.setPrice(new BigDecimal("0"));
//        pistol.setCaliber("aaa");
//
//        assertEquals(pistol.getManufacturer(), "a");
//        assertEquals(pistol.getName(), "aa");
//        assertEquals(pistol.getPrice(), new BigDecimal("0"));
//        assertEquals(pistol.getCaliber(), "aaa");
//    }
//
//    @Test
//    void RifleTest() {
//        Rifle rifle = new Rifle(1L, "Ruger", "PPC", new BigDecimal("4000"), "22LR", Float.valueOf("15.6"));
//        assertNotNull(rifle);
//        assertNull(rifle.getId());
//        assertEquals(rifle.getSerialNumber(), 1L);
//        assertEquals(rifle.getType(), "Rifle");
//        assertEquals(rifle.getManufacturer(), "Ruger");
//        assertEquals(rifle.getName(), "PPC");
//        assertEquals(rifle.getPrice(), new BigDecimal("4000"));
//        assertEquals(rifle.getCaliber(), "22LR");
//        assertEquals(rifle.getLength(), Float.valueOf("15.6"));
//
//        rifle.setManufacturer("a");
//        rifle.setName("aa");
//        rifle.setPrice(new BigDecimal("0"));
//        rifle.setCaliber("aaa");
//        rifle.setLength(Float.valueOf("1"));
//
//        assertEquals(rifle.getManufacturer(), "a");
//        assertEquals(rifle.getName(), "aa");
//        assertEquals(rifle.getPrice(), new BigDecimal("0"));
//        assertEquals(rifle.getCaliber(), "aaa");
//        assertEquals(rifle.getLength(), Float.valueOf("1"));
//    }
//
//    @Test
//    void HandGrenadeTest() {
//        HandGrenade grenade = new HandGrenade(1L, "Smolinus Inc.", "Ovirt mk 2", new BigDecimal("2135"), 9001, GrenadeType.Fag);
//        assertNotNull(grenade);
//        assertNull(grenade.getId());
//        assertEquals(grenade.getSerialNumber(), 1L);
//        assertEquals(grenade.getType(), "HandGrenade");
//        assertEquals(grenade.getManufacturer(), "Smolinus Inc.");
//        assertEquals(grenade.getName(), "Ovirt mk 2");
//        assertEquals(grenade.getPrice(), new BigDecimal("2135"));
//        assertEquals(grenade.getPower(), 9001);
//        assertEquals(grenade.getGrenadeType(), GrenadeType.Fag);
//
//        grenade.setManufacturer("a");
//        grenade.setName("aa");
//        grenade.setPrice(new BigDecimal("0"));
//        grenade.setPower(0);
//        grenade.setGrenadeType(GrenadeType.Cluster);
//
//        assertEquals(grenade.getManufacturer(), "a");
//        assertEquals(grenade.getName(), "aa");
//        assertEquals(grenade.getPrice(), new BigDecimal("0"));
//        assertEquals(grenade.getPower(), 0);
//        assertEquals(grenade.getGrenadeType(), GrenadeType.Cluster);
//    }
//
//    @Test
//    void RecreationalMcNukeTest() {
//        RecreationalMcNuke nuke = new RecreationalMcNuke(3L, "Smolinus Inc.", "VIRTuL", new BigDecimal("69420"), 1234);
//        assertNotNull(nuke);
//        assertNull(nuke.getId());
//        assertEquals(nuke.getSerialNumber(), 3L);
//        assertEquals(nuke.getType(), "Nuke");
//        assertEquals(nuke.getManufacturer(), "Smolinus Inc.");
//        assertEquals(nuke.getName(), "VIRTuL");
//        assertEquals(nuke.getPrice(), new BigDecimal("69420"));
//        assertEquals(nuke.getPower(), 1234);
//
//        nuke.setManufacturer("a");
//        nuke.setName("aa");
//        nuke.setPrice(new BigDecimal("0"));
//        nuke.setPower(0);
//
//        assertEquals(nuke.getManufacturer(), "a");
//        assertEquals(nuke.getName(), "aa");
//        assertEquals(nuke.getPrice(), new BigDecimal("0"));
//        assertEquals(nuke.getPower(), 0);
//    }
//}
