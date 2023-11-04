package repository;


import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Rifle;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.WeaponRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeaponRepositoryTest {

    /* CREATE TESTS */
    @Test
    @Order(1)
    void WeaponRepositoryCreateWeaponSuccessTest()  {

        try(WeaponRepository weaponRepository = new WeaponRepository()){
            Map<String, String> map = new HashMap<>();
            map.put("serialNumber", "1");
            map.put("manufacturer", "Glock");
            map.put("name", "Glock 19");
            map.put("price", "2500");
            map.put("caliber", "9mm");
            Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
            assertDoesNotThrow(() -> weaponRepository.add(pistol));

            Weapon weapon = assertDoesNotThrow(()-> weaponRepository.get(1L));
            assertNotNull(weapon);
            assertInstanceOf(Pistol.class, weapon);
            Pistol pistol2 = (Pistol) weapon;
            assertEquals(pistol2.getSerialNumber(), 1L);
            assertEquals(pistol2.getManufacturer(), "Glock");
            assertEquals(pistol2.getName(), "Glock 19");
            assertEquals(pistol2.getPrice(), new BigDecimal(2500));
            assertEquals(pistol2.getCaliber(), "9mm");

            assertDoesNotThrow(()-> weaponRepository.remove(1L));

            assertThrows(RepositoryException.class, () -> weaponRepository.get(1L));

        }

    }

    @Test
    @Order(2)
    void WeaponRepositoryCreateWeaponFailureTest() {

       try( WeaponRepository weaponRepository = new WeaponRepository()) {
           assertThrows(RepositoryException.class, () -> weaponRepository.add(null));
       }
    }

    /* READ TESTS */

    @Test
    @Order(3)
    void WeaponRepositoryGetSuccessTest() {
        try( WeaponRepository weaponRepository = new WeaponRepository()) {
            Map<String, String> map = new HashMap<>();
            map.put("serialNumber", "1");
            map.put("manufacturer", "Glock");
            map.put("name", "Glock 19");
            map.put("price", "2500");
            map.put("caliber", "9mm");
            Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
            assertDoesNotThrow(() -> weaponRepository.add(pistol));
            assertDoesNotThrow(() -> weaponRepository.get(1L));
        }
    }
    @Test
    @Order(4)
    void WeaponRepositoryGetFailureTest() {
        try(WeaponRepository weaponRepository = new WeaponRepository()) {
            assertThrows(RepositoryException.class,() -> weaponRepository.get(1L));
        }

    }
    @Test
    @Order(5)
    void WeaponRepositoryGetAllTest() {
        try(WeaponRepository weaponRepository = new WeaponRepository()) {
            Map<String, String> map = new HashMap<>();
            map.put("serialNumber", "1");
            map.put("manufacturer", "Glock");
            map.put("name", "Glock 19");
            map.put("price", "2500");
            map.put("caliber", "9mm");
            Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
            assertDoesNotThrow(() -> weaponRepository.add(pistol));
            Map<String, String> map2 = new HashMap<>();
            map2.put("type", WeaponTypeEnum.RIFLE.toString());
            map2.put("serialNumber", "2137");
            map2.put("manufacturer", "Ruger");
            map2.put("name", "PPC");
            map2.put("price", "4000");
            map2.put("caliber", "22LR");
            map2.put("length", "15.6");
            Rifle rifleTest = WeaponFactory.manufactureWeapon(WeaponTypeEnum.RIFLE, map2);
            assertDoesNotThrow(() -> weaponRepository.add(rifleTest));
            assertEquals(2, weaponRepository.getAll().size());
            Pistol pistol2 = (Pistol) weaponRepository.getAll().stream().filter(weapon -> Objects.equals(weapon.getSerialNumber(), 1L)).findFirst().get();
            assertNotNull(pistol2);
            assertEquals( 1L,pistol2.getSerialNumber());
            assertEquals("Pistol",pistol2.getType());
            assertEquals("Glock",pistol2.getManufacturer());
            assertEquals("Glock 19",pistol2.getName());
            assertEquals(new BigDecimal("2500"),pistol2.getPrice());
            assertEquals( "9mm",pistol2.getCaliber());
            Rifle rifle = (Rifle) weaponRepository.getAll().stream().filter(weapon -> Objects.equals(weapon.getSerialNumber(), 2137L)).findFirst().get();
            assertNotNull(rifle);
            assertEquals(2137L,rifle.getSerialNumber());
            assertEquals("Rifle",rifle.getType() );
            assertEquals("Ruger",rifle.getManufacturer());
            assertEquals( "PPC",rifle.getName());
            assertEquals( new BigDecimal("4000"),rifle.getPrice());
            assertEquals( "22LR",rifle.getCaliber());
            assertEquals(Float.valueOf("15.6"),rifle.getLength());
        }

    }
    @Test
    @Order(6)
    void WeaponRepositoryFindTest() {
        try(WeaponRepository weaponRepository = new WeaponRepository()) {
            Map<String, String> map = new HashMap<>();
            map.put("serialNumber", "1");
            map.put("manufacturer", "Glock");
            map.put("name", "Glock 19");
            map.put("price", "2500");
            map.put("caliber", "9mm");
            Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
            assertDoesNotThrow(() -> weaponRepository.add(pistol));
            Map<String, String> map2 = new HashMap<>();
            map2.put("type", WeaponTypeEnum.RIFLE.toString());
            map2.put("serialNumber", "2137");
            map2.put("manufacturer", "Ruger");
            map2.put("name", "PPC");
            map2.put("price", "4000");
            map2.put("caliber", "22LR");
            map2.put("length", "15.6");
            Rifle rifleTest = WeaponFactory.manufactureWeapon(WeaponTypeEnum.RIFLE, map2);
            assertDoesNotThrow(() -> weaponRepository.add(rifleTest));
            assertEquals(1, weaponRepository.find(eq("type", "Rifle")).size());
            Rifle rifle = (Rifle) weaponRepository.getAll().stream().filter(weapon -> Objects.equals(weapon.getType(), "Rifle")).findFirst().get();
            assertNotNull(rifle);
            assertEquals(2137L,rifle.getSerialNumber());
            assertEquals("Rifle",rifle.getType());
            assertEquals("Ruger",rifle.getManufacturer());
            assertEquals( "PPC",rifle.getName());
            assertEquals( new BigDecimal("4000"),rifle.getPrice());
            assertEquals( "22LR",rifle.getCaliber());
            assertEquals(Float.valueOf("15.6"),rifle.getLength());
        }

    }

    /* UPDATE TESTS */
    /*  XDDD */

    /*DELETE TESTS */

    @Test
    @Order(9)
    void WeaponRepositoryDeleteWeaponSuccessTest() {

        try(WeaponRepository weaponRepository = new WeaponRepository()) {
            Map<String, String> map = new HashMap<>();
            map.put("serialNumber", "1");
            map.put("manufacturer", "Glock");
            map.put("name", "Glock 19");
            map.put("price", "2500");
            map.put("caliber", "9mm");
            Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
            assertDoesNotThrow(() -> weaponRepository.add(pistol));
            assertDoesNotThrow(() -> weaponRepository.remove(1L));
        }
    }

    @Test
    @Order(10)
    void WeaponRepositoryDeleteWeaponFailureTest() {
        try( WeaponRepository weaponRepository = new WeaponRepository()) {
            assertThrows(RepositoryException.class, () -> weaponRepository.remove(5L));
        }
    }
//
//    @Test
//    void WeaponRepositoryGetAllWeaponsTest() {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        WeaponRepository weaponRepository = new WeaponRepository(entityManager);
//        assertDoesNotThrow(weaponRepository::getAllWeapons);
//        entityManagerFactory.close();
//    }
}
