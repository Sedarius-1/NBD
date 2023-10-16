package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
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
        assertDoesNotThrow(()->weaponRepository.add(pistol));

        Weapon weapon = weaponRepository.get(1L);
        assertNotNull(weapon);
        assertInstanceOf(Pistol.class, weapon);
        Pistol pistol2 = (Pistol) weapon;
        assertEquals(pistol2.getSerialNumber(), 1L);
        assertEquals(pistol2.getManufacturer(), "Glock");
        assertEquals(pistol2.getName(), "Glock 19");
        assertEquals(pistol2.getPrice(), new BigDecimal(2500));
        assertEquals(pistol2.getCaliber(), "9mm");

        weaponRepository.remove(pistol);

        assertThrows(RepositoryException.class, ()-> weaponRepository.get(1L));

        entityManagerFactory.close();
    }

    @Test
    void WeaponRepositoryAddFailureTest()  {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        WeaponRepository weaponRepository = new WeaponRepository(entityManager);
        assertThrows(RepositoryException.class, ()->weaponRepository.add(null));
        entityManagerFactory.close();
    }

    @Test
    void WeaponRepositoryRemoveFailureTest()  {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        WeaponRepository weaponRepository = new WeaponRepository(entityManager);
        assertThrows(RepositoryException.class, ()->weaponRepository.remove(null));
        entityManagerFactory.close();
    }

    @Test
    void WeaponRepositoryGetAllWeaponsTest()  {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        WeaponRepository weaponRepository = new WeaponRepository(entityManager);
        assertDoesNotThrow(weaponRepository::getAllWeapons);
        entityManagerFactory.close();
    }
}
