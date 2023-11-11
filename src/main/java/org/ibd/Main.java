package org.ibd;


import org.ibd.controller.GunShop;
import org.ibd.enums.ClientParamEnum;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.manager.PurchaseManager;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.purchases.PurchaseMap;
import org.ibd.repository.PurchaseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;


public class Main {

    public static void main(String[] args) {
        PurchaseManager purchaseManager = new PurchaseManager(new PurchaseRepository());
        GunShop gunShop = new GunShop();
        gunShop.registerClient(1L, "Name", "Surname", "Address", LocalDate.of(2000, 1, 1), BigDecimal.valueOf(1000000));

        gunShop.updateClient(ClientParamEnum.NAME, "Stanislaw", 1L);
        gunShop.registerClient(2L, "Name", "Surname1", "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
        System.out.println(gunShop.getClientInfo(1L));
        System.out.println(gunShop.getClientBalance(1L));
        gunShop.getAllClientsInfo();
        gunShop.findClientsInfo(eq("surname", "Surname1"));
        Map<String, String> rifleMap = new HashMap<>();
        rifleMap.put("type", WeaponTypeEnum.RIFLE.toString());
        rifleMap.put("serialNumber", "12");
        rifleMap.put("manufacturer", "Ruger");
        rifleMap.put("name", "PPC");
        rifleMap.put("price", "4000");
        rifleMap.put("caliber", "22LR");
        rifleMap.put("length", "15.6");
        gunShop.registerWeapon(WeaponTypeEnum.RIFLE, rifleMap);
        Map<String, String> hgMap = new HashMap<>();
        hgMap.put("type", WeaponTypeEnum.HANDGRENADE.toString());
        hgMap.put("serialNumber", "13");
        hgMap.put("manufacturer", "Smolinus Inc.");
        hgMap.put("name", "Ovirt mk 2");
        hgMap.put("price", "2135");
        hgMap.put("power", "9001");
        hgMap.put("grenadeType", GrenadeType.Fag.toString());
        gunShop.registerWeapon(WeaponTypeEnum.HANDGRENADE, hgMap);
        Map<String, String> nukeMap = new HashMap<>();
        nukeMap.put("type", WeaponTypeEnum.MCNUKE.toString());
        nukeMap.put("serialNumber", "14");
        nukeMap.put("manufacturer", "Smolinus Inc.");
        nukeMap.put("name", "VIRTuL");
        nukeMap.put("price", "69420");
        nukeMap.put("power", "1");
        gunShop.registerWeapon(WeaponTypeEnum.MCNUKE, nukeMap);
        Map<String, String> pistolMap = new HashMap<>();
        pistolMap.put("type", WeaponTypeEnum.PISTOL.toString());
        pistolMap.put("serialNumber", "15");
        pistolMap.put("manufacturer", "Glock");
        pistolMap.put("name", "Glock 19");
        pistolMap.put("price", "213.7");
        pistolMap.put("caliber", "9mm");
        gunShop.registerWeapon(WeaponTypeEnum.PISTOL, pistolMap);
        gunShop.getAllWeaponsInfo();
        gunShop.findWeaponsInfo(eq("manufacturer", "Smolinus Inc."));
        gunShop.registerPurchase(1L,gunShop.getClient(1L), gunShop.getWeapon(15L));
        System.out.printf(gunShop.formatPurchaseInfo(purchaseManager.getPurchase(1L)));
        gunShop.registerPurchase(2L,gunShop.getClient(1L), gunShop.getWeapon(15L));
        System.out.printf(gunShop.formatPurchaseInfo(purchaseManager.getPurchase(2L)));
    }
}