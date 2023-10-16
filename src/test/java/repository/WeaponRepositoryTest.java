package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.WeaponRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponRepositoryTest {
    @Test
    void WeaponRepositoryCreateWeaponTest() throws RepositoryException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        WeaponRepository weaponRepository = new WeaponRepository(entityManager);
        Map<String, String> map = new HashMap<>();
        map.put("serialNumber", "1");
        map.put("manufacturer", "Glock");
        map.put("name", "Glock 19");
        map.put("price", "2500");
        map.put("caliber", "9mm");
        Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
        weaponRepository.addWeapon(pistol); // TODO: add doesNotThrow assert

        Weapon weapon = weaponRepository.getWeapon(1L);
        assertNotNull(weapon);
        assertInstanceOf(Pistol.class, weapon);
        Pistol pistol2 = (Pistol) weapon;
        assertEquals(pistol2.getSerialNumber(), 1L);
        assertEquals(pistol2.getManufacturer(), "Glock");
        assertEquals(pistol2.getName(), "Glock 19");
        assertEquals(pistol2.getPrice(), new BigDecimal(2500));
        assertEquals(pistol2.getCaliber(), "9mm");

        weaponRepository.removeWeapon(pistol2); // fuk

        assertNull(weaponRepository.getWeapon(1L));

        entityManagerFactory.close();
    }

    // TODO: modify tests
    // TODO: bad case tests
}
