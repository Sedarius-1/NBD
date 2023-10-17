package org.ibd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.controller.GunShop;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Rifle;
import org.ibd.model.weapons.Weapon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    protected static final Logger logger = (Logger) LogManager.getLogger();

    public static void main(String[] args) {
        GunShop gunShop = new GunShop();
        Client client = gunShop.registerClient(1L, "User", "Userer", "Address", LocalDate.of(2003, 2, 5), new BigDecimal("600"));
        Map<String, String> rifleMap = new HashMap<>();
        rifleMap.put("serialNumber", "1");
        rifleMap.put("manufacturer", "uwu");
        rifleMap.put("name", "test");
        rifleMap.put("price", "300");
        rifleMap.put("caliber", "uwu2");
        rifleMap.put("length", "50");
        Rifle rifle = (Rifle) gunShop.registerWeapon(WeaponTypeEnum.RIFLE, rifleMap);
        rifleMap.put("serialNumber", "2");
        rifleMap.put("price", "500");
        Rifle rifle2 = (Rifle) gunShop.registerWeapon(WeaponTypeEnum.RIFLE, rifleMap);
        Purchase purchase = gunShop.registerPurchase(1L, client, rifle2);
        List<Weapon> availableWeapons = gunShop.getAvailableWeapons();
        logger.info(availableWeapons.size());
        logger.info("All purchases:"+gunShop.getAllPurchases().size());
    }
}