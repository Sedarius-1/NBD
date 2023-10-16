package manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.manager.ClientManager;
import org.ibd.manager.WeaponManager;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Rifle;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.WeaponRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponManagerTest {
    @Test
    void PersistWeaponTest() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        WeaponManager weaponManager = new WeaponManager(new WeaponRepository(entityManager));
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
        assertNotNull(rifle.getId());
        assertEquals(rifle.getSerialNumber(), 2137L);
        assertEquals(rifle.getType(), "Rifle");
        assertEquals(rifle.getManufacturer(), "Ruger");
        assertEquals(rifle.getName(), "PPC");
        assertEquals(rifle.getPrice(), new BigDecimal("4000"));
        assertEquals(rifle.getCaliber(), "22LR");
        assertEquals(rifle.getLength(), Float.valueOf("15.6"));
        entityManagerFactory.close();
    }

    // TODO: add bad case tests
}
