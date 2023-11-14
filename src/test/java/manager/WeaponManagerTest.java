package manager;

import org.ibd.enums.WeaponTypeEnum;
import org.ibd.manager.WeaponManager;
import org.ibd.model.weapons.Rifle;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.WeaponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

public class WeaponManagerTest {
    WeaponManager weaponManager;

    @BeforeEach
    void InitWeaponManager() {
        weaponManager = new WeaponManager(new WeaponRepository());
    }

    /* CREATE TESTS */
    @Test
    @Order(1)
    void PersistWeaponTest() {
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map);
        Weapon weapon = weaponManager.getWeapon(2137L);
        assertInstanceOf(Rifle.class, weapon);
        Rifle rifle = (Rifle) weapon;
        assertNotNull(rifle);
        assertEquals(rifle.getSerialNumber(), 2137L);
        assertEquals(rifle.getType(), "Rifle");
        assertEquals(rifle.getManufacturer(), "Ruger");
        assertEquals(rifle.getName(), "PPC");
        assertEquals(rifle.getPrice(), new BigDecimal("4000"));
        assertEquals(rifle.getCaliber(), "22LR");
        assertEquals(rifle.getLength(), Float.valueOf("15.6"));
    }

    @Test
    @Order(2)
    void WeaponManagerRegisterWeaponFail() {
        assertFalse(weaponManager.registerWeapon(null, null));
        assertFalse(weaponManager.registerWeapon(WeaponTypeEnum.PISTOL, null));
    }

    /* READ TESTS */
    @Test
    @Order(3)
    void WeaponManagerGetAllWeaponsSuccess() {
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map);
        assertDoesNotThrow(weaponManager::getAllWeapons);
        assertNotNull(weaponManager.getAllWeapons());
        assertEquals(1, weaponManager.getAllWeapons().size());
    }

    @Test
    void WeaponManagerFindWeaponSuccess() {
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map);
        Map<String, String> map2 = new HashMap<>();
        map2.put("serialNumber", "2138");
        map2.put("manufacturer", "Ruger");
        map2.put("name", "PPC");
        map2.put("price", "1234");
        map2.put("caliber", "9mm");
        map2.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map2);
        assertEquals(1, weaponManager.findWeapons(eq("serialNumber", 2137L)).size());
        assertEquals(2, weaponManager.findWeapons(eq("manufacturer", "Ruger")).size());
        assertEquals(1, weaponManager.findWeapons(eq("price", new BigDecimal("4000"))).size());
        assertEquals(0, weaponManager.findWeapons(eq("caliber", "50")).size());
    }

    @Test
    @Order(4)
    void WeaponManagerGetWeaponFail() {
        assertNull(weaponManager.getWeapon((null)));
    }

    /* UPDATE TESTS */
    @Test
    @Order(5)
    void WeaponManagerChangePrice() {
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map);
        weaponManager.changePrice(2137L, BigDecimal.ONE);
        assertEquals(weaponManager.getWeapon(2137L).getPrice(), BigDecimal.ONE);
    }

    @Test
    @Order(5)
    void WeaponManagerChangePriceFailOnNull() {
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map);
        assertFalse(weaponManager.changePrice(2137L, null));
    }

    /* DELETE TESTS */
    @Test
    void WeaponManagerUnregisterWeaponSuccess() {
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        weaponManager.registerWeapon(WeaponTypeEnum.RIFLE, map);
        assertTrue(weaponManager.unregisterWeapon(2137L));
    }

    @Test
    void WeaponManagerUnregisterWeaponFail() {
        assertFalse(weaponManager.unregisterWeapon(null));
    }


}
