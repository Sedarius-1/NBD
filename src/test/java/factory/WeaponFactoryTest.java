package factory;

import org.ibd.enums.WeaponTypeEnum;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.weapons.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponFactoryTest {

    @ParameterizedTest
    @ArgumentsSource(MapTooFewArgumentsProvider.class)
    void TooFewArgumentsTest(Map<String, String> badParams) {
        WeaponTypeEnum type = WeaponTypeEnum.valueOf(badParams.get("type"));
        badParams.remove("type");
        Weapon weapon = WeaponFactory.manufactureWeapon(type, badParams);
        assertNull(weapon);
    }

    static class MapTooFewArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            // Any
            Map<String, String> map0 = new HashMap<>();
            map0.put("type", WeaponTypeEnum.RIFLE.toString());
            map0.put("manufacturer", "Ruger");
            map0.put("name", "PPC");

            // Rifle
            Map<String, String> map1 = new HashMap<>();
            map1.put("type", WeaponTypeEnum.RIFLE.toString());
            map1.put("serialNumber", "2137");
            map1.put("manufacturer", "Ruger");
            map1.put("name", "PPC");
            map1.put("price", "4000");
            map1.put("caliber", "22LR");
            //map1.put("length", "15.6");

            // Pistol
            Map<String, String> map2 = new HashMap<>();
            map2.put("type", WeaponTypeEnum.PISTOL.toString());
            map2.put("serialNumber", "2137");
            map2.put("manufacturer", "Glock");
            map2.put("name", "Glock 19");
            map2.put("price", "2500");
            //map2.put("caliber", "9mm");

            // Grenade
            Map<String, String> map3 = new HashMap<>();
            map3.put("type", WeaponTypeEnum.HANDGRENADE.toString());
            map3.put("serialNumber", "2137");
            map3.put("manufacturer", "Smolinus Inc.");
            map3.put("name", "Ovirt mk 2");
            map3.put("price", "2135");
            map3.put("power", "9001");
            //map3.put("grenadeType", GrenadeType.Fag.toString());

            // McNuke
            Map<String, String> map4 = new HashMap<>();
            map4.put("type", WeaponTypeEnum.MCNUKE.toString());
            map4.put("serialNumber", "2137");
            map4.put("manufacturer", "Smolinus Inc.");
            map4.put("name", "VIRTuL");
            map4.put("price", "69420");
            //map4.put("power", "0");

            return Stream.of(
                    Arguments.of(map0),
                    Arguments.of(map1),
                    Arguments.of(map2),
                    Arguments.of(map3),
                    Arguments.of(map4)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(MapUnparsableArgumentsProvider.class)
    void UnparsableArgumentsTest(Map<String, String> badParams) {
        WeaponTypeEnum type = WeaponTypeEnum.valueOf(badParams.get("type"));
        badParams.remove("type");
        Weapon weapon = WeaponFactory.manufactureWeapon(type, badParams);
        assertNull(weapon);
    }

    static class MapUnparsableArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            // Any - bad price
            Map<String, String> map0 = new HashMap<>();
            map0.put("type", WeaponTypeEnum.PISTOL.toString());
            map0.put("serialNumber", "2137");
            map0.put("manufacturer", "Glock");
            map0.put("name", "Glock 19");
            map0.put("price", "dosyć drogo");
            map0.put("caliber", "9mm");

            // Any - bad serial
            Map<String, String> map1 = new HashMap<>();
            map1.put("type", WeaponTypeEnum.PISTOL.toString());
            map1.put("serialNumber", "2a137");
            map1.put("manufacturer", "Glock");
            map1.put("name", "Glock 19");
            map1.put("price", "2500");
            map1.put("caliber", "9mm");

            // Rifle - bad length
            Map<String, String> map2 = new HashMap<>();
            map2.put("type", WeaponTypeEnum.RIFLE.toString());
            map2.put("serialNumber", "2137");
            map2.put("manufacturer", "Ruger");
            map2.put("name", "PPC");
            map2.put("price", "4000");
            map2.put("caliber", "22LR");
            map2.put("length", "smol riefel");

            // McNuke - bad power
            Map<String, String> map3 = new HashMap<>();
            map3.put("type", WeaponTypeEnum.MCNUKE.toString());
            map3.put("serialNumber", "2137");
            map3.put("manufacturer", "Smolinus Inc.");
            map3.put("name", "VIRTuL");
            map3.put("price", "69420");
            map3.put("power", "jebutna");

            // Grenade - bad power
            Map<String, String> map4 = new HashMap<>();
            map4.put("type", WeaponTypeEnum.HANDGRENADE.toString());
            map4.put("serialNumber", "2137");
            map4.put("manufacturer", "Smolinus Inc.");
            map4.put("name", "Ovirt mk 2");
            map4.put("price", "2135");
            map4.put("power", "mogło być więcej");
            map4.put("grenadeType", GrenadeType.Fag.toString());

            // Grenade - bad type
            Map<String, String> map5 = new HashMap<>();
            map5.put("type", WeaponTypeEnum.HANDGRENADE.toString());
            map5.put("serialNumber", "2137");
            map5.put("manufacturer", "Smolinus Inc.");
            map5.put("name", "Ovirt mk 2");
            map5.put("price", "2135");
            map5.put("power", "1");
            map5.put("grenadeType", "idk");

            return Stream.of(
                    Arguments.of(map0),
                    Arguments.of(map1),
                    Arguments.of(map2),
                    Arguments.of(map3),
                    Arguments.of(map4),
                    Arguments.of(map5)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(MissingArgumentsProvider.class)
    void MissingArgumentsTest(Map<String, String> badParams) {
        WeaponTypeEnum type = WeaponTypeEnum.valueOf(badParams.get("type"));
        badParams.remove("type");
        Weapon weapon = WeaponFactory.manufactureWeapon(type, badParams);
        assertNull(weapon);
    }

    static class MissingArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            // Any - missing manufacturer
            Map<String, String> map0 = new HashMap<>();
            map0.put("type", WeaponTypeEnum.PISTOL.toString());
            map0.put("serialNumber", "2137");
            map0.put("notmanufacturer", "Glock");
            map0.put("name", "Glock 19");
            map0.put("price", "2500");
            map0.put("caliber", "9mm");

            // Any - missing serial
            Map<String, String> map9 = new HashMap<>();
            map9.put("type", WeaponTypeEnum.PISTOL.toString());
            map9.put("notserialNumber", "2137");
            map9.put("manufacturer", "Glock");
            map9.put("name", "Glock 19");
            map9.put("price", "2500");
            map9.put("caliber", "9mm");

            // Any - missing name
            Map<String, String> map1 = new HashMap<>();
            map1.put("type", WeaponTypeEnum.PISTOL.toString());
            map1.put("serialNumber", "2137");
            map1.put("manufacturer", "Glock");
            map1.put("notname", "Glock 19");
            map1.put("price", "2500");
            map1.put("caliber", "9mm");

            // Any - missing price
            Map<String, String> map2 = new HashMap<>();
            map2.put("type", WeaponTypeEnum.PISTOL.toString());
            map2.put("serialNumber", "2137");
            map2.put("manufacturer", "Glock");
            map2.put("name", "Glock 19");
            map2.put("notprice", "2500");
            map2.put("caliber", "9mm");

            // Pistol - missing caliber
            Map<String, String> map3 = new HashMap<>();
            map3.put("type", WeaponTypeEnum.PISTOL.toString());
            map3.put("serialNumber", "2137");
            map3.put("manufacturer", "Glock");
            map3.put("name", "Glock 19");
            map3.put("price", "2500");
            map3.put("notcaliber", "9mm");

            // Rifle - missing caliber
            Map<String, String> map4 = new HashMap<>();
            map4.put("type", WeaponTypeEnum.RIFLE.toString());
            map4.put("serialNumber", "2137");
            map4.put("manufacturer", "Ruger");
            map4.put("name", "PPC");
            map4.put("price", "4000");
            map4.put("notcaliber", "22LR");
            map4.put("length", "15.6");

            // Rifle - missing length
            Map<String, String> map5 = new HashMap<>();
            map5.put("type", WeaponTypeEnum.RIFLE.toString());
            map5.put("serialNumber", "2137");
            map5.put("manufacturer", "Ruger");
            map5.put("name", "PPC");
            map5.put("price", "4000");
            map5.put("caliber", "22LR");
            map5.put("notlength", "15.6");


            // Grenade - missing power
            Map<String, String> map6 = new HashMap<>();
            map6.put("type", WeaponTypeEnum.HANDGRENADE.toString());
            map6.put("serialNumber", "2137");
            map6.put("manufacturer", "Smolinus Inc.");
            map6.put("name", "Ovirt mk 2");
            map6.put("price", "2135");
            map6.put("notpower", "9001");
            map6.put("grenadeType", GrenadeType.Fag.toString());

            // Grenade - missing type
            Map<String, String> map7 = new HashMap<>();
            map7.put("type", WeaponTypeEnum.HANDGRENADE.toString());
            map7.put("serialNumber", "2137");
            map7.put("manufacturer", "Smolinus Inc.");
            map7.put("name", "Ovirt mk 2");
            map7.put("price", "2135");
            map7.put("power", "9001");
            map7.put("notgrenadeType", GrenadeType.Fag.toString());

            // McNuke - missing power
            Map<String, String> map8 = new HashMap<>();
            map8.put("type", WeaponTypeEnum.MCNUKE.toString());
            map8.put("manufacturer", "Smolinus Inc.");
            map8.put("name", "VIRTuL");
            map8.put("price", "69420");
            map8.put("notpower", "0");

            return Stream.of(
                    Arguments.of(map0),
                    Arguments.of(map1),
                    Arguments.of(map2),
                    Arguments.of(map3),
                    Arguments.of(map4),
                    Arguments.of(map5),
                    Arguments.of(map6),
                    Arguments.of(map7),
                    Arguments.of(map8),
                    Arguments.of(map9)
            );
        }
    }

    @Test
    void MakePistolTest() {
        Map<String, String> map = new HashMap<>();
        map.put("type", WeaponTypeEnum.PISTOL.toString());
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Glock");
        map.put("name", "Glock 19");
        map.put("price", "213.7");
        map.put("caliber", "9mm");
        Pistol pistol = WeaponFactory.manufactureWeapon(WeaponTypeEnum.PISTOL, map);
        assertNotNull(pistol);
        assertEquals(pistol.getSerialNumber(), 2137L);
        assertEquals(pistol.getType(), "Pistol");
        assertEquals(pistol.getManufacturer(), "Glock");
        assertEquals(pistol.getName(), "Glock 19");
        assertEquals(pistol.getPrice(), new BigDecimal("213.7"));
        assertEquals(pistol.getCaliber(), "9mm");
    }

    @Test
    void MakeRifleTest() {
        Map<String, String> map = new HashMap<>();
        map.put("type", WeaponTypeEnum.RIFLE.toString());
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Ruger");
        map.put("name", "PPC");
        map.put("price", "4000");
        map.put("caliber", "22LR");
        map.put("length", "15.6");
        Rifle rifle = WeaponFactory.manufactureWeapon(WeaponTypeEnum.RIFLE, map);
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
    void MakeHandGrenadeTest() {
        Map<String, String> map = new HashMap<>();
        map.put("type", WeaponTypeEnum.HANDGRENADE.toString());
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Smolinus Inc.");
        map.put("name", "Ovirt mk 2");
        map.put("price", "2135");
        map.put("power", "9001");
        map.put("grenadeType", GrenadeType.Fag.toString());
        HandGrenade grenade = WeaponFactory.manufactureWeapon(WeaponTypeEnum.HANDGRENADE, map);
        assertNotNull(grenade);
        assertEquals(grenade.getSerialNumber(), 2137L);
        assertEquals(grenade.getType(), "HandGrenade");
        assertEquals(grenade.getManufacturer(), "Smolinus Inc.");
        assertEquals(grenade.getName(), "Ovirt mk 2");
        assertEquals(grenade.getPrice(), new BigDecimal("2135"));
        assertEquals(grenade.getPower(), 9001);
        assertEquals(grenade.getGrenadeType(), GrenadeType.Fag);
    }

    @Test
    void MakeMcNukeTest() {
        Map<String, String> map = new HashMap<>();
        map.put("type", WeaponTypeEnum.MCNUKE.toString());
        map.put("serialNumber", "2137");
        map.put("manufacturer", "Smolinus Inc.");
        map.put("name", "VIRTuL");
        map.put("price", "69420");
        map.put("power", "1");
        RecreationalMcNuke nuke = WeaponFactory.manufactureWeapon(WeaponTypeEnum.MCNUKE, map);
        assertNotNull(nuke);
        assertEquals(nuke.getSerialNumber(), 2137L);
        assertEquals(nuke.getType(), "Nuke");
        assertEquals(nuke.getManufacturer(), "Smolinus Inc.");
        assertEquals(nuke.getName(), "VIRTuL");
        assertEquals(nuke.getPrice(), new BigDecimal("69420"));
        assertEquals(nuke.getPower(), 1);
    }
}


