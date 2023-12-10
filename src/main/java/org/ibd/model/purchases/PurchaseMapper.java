package org.ibd.model.purchases;

import org.ibd.factory.PurchaseFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.HandGrenade;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.RecreationalMcNuke;
import org.ibd.model.weapons.Rifle;

import java.util.Objects;

public class PurchaseMapper {

    public static void convertPurchaseToPurchaseMap(PurchaseMap purchaseMap, Purchase purchase) {
        purchaseMap.setPurchaseId(purchase.getPurchaseId());
        purchaseMap.setClientId(purchase.getClient().getClientId());
        purchaseMap.setName(purchase.getClient().getName());
        purchaseMap.setSurname(purchase.getClient().getSurname());
        purchaseMap.setAddress(purchase.getClient().getAddress());
        purchaseMap.setBirth(purchase.getClient().getBirth());
        purchaseMap.setBalance(purchase.getClient().getBalance());
        purchaseMap.setSerialNumber(purchase.getWeapon().getSerialNumber());
        purchaseMap.setManufacturer(purchase.getWeapon().getManufacturer());
        purchaseMap.setWeaponName(purchase.getWeapon().getName());
        purchaseMap.setPrice(purchase.getWeapon().getPrice());
        purchaseMap.setType(purchase.getWeapon().getType());
        if (Objects.equals(purchaseMap.getType(), "Nuke")) {
            RecreationalMcNuke nuke = (RecreationalMcNuke) purchase.getWeapon();
            purchaseMap.setPower(nuke.getPower());
        }
        if (Objects.equals(purchaseMap.getType(), "HandGrenade")) {
            HandGrenade grenade = (HandGrenade) purchase.getWeapon();
            purchaseMap.setPower(grenade.getPower());
            purchaseMap.setGrenadeType(grenade.getGrenadeType());
        }
        if (Objects.equals(purchaseMap.getType(), "Pistol")) {
            Pistol pistol = (Pistol) purchase.getWeapon();
            purchaseMap.setCaliber(pistol.getCaliber());
        }
        if (Objects.equals(purchaseMap.getType(), "Rifle")) {
            Rifle rifle = (Rifle) purchase.getWeapon();
            purchaseMap.setCaliber(rifle.getCaliber());
            purchaseMap.setLength(rifle.getLength());
        }
    }

    public static Purchase convertPurchaseMapToPurchase(PurchaseMap purchaseMap) {
        Client client = new Client(purchaseMap.getClientId(),
                purchaseMap.getName(),
                purchaseMap.getSurname(),
                purchaseMap.getAddress(),
                purchaseMap.getBirth(),
                purchaseMap.getBalance());
        return switch (purchaseMap.getType()) {
            case ("Pistol") -> {
                Pistol pistol = new Pistol(purchaseMap.getSerialNumber(),
                        purchaseMap.getManufacturer(),
                        purchaseMap.getWeaponName(),
                        purchaseMap.getPrice(),
                        purchaseMap.getCaliber());
                yield PurchaseFactory.createPurchase(purchaseMap.getPurchaseId(), client, pistol);
            }
            case ("Rifle") -> {
                Rifle rifle = new Rifle(purchaseMap.getSerialNumber(),
                        purchaseMap.getManufacturer(),
                        purchaseMap.getWeaponName(),
                        purchaseMap.getPrice(),
                        purchaseMap.getCaliber(),
                        purchaseMap.getLength());
                yield PurchaseFactory.createPurchase(purchaseMap.getPurchaseId(), client, rifle);
            }
            case ("HandGrenade") -> {
                HandGrenade handGrenade = new HandGrenade(purchaseMap.getSerialNumber(),
                        purchaseMap.getManufacturer(),
                        purchaseMap.getWeaponName(),
                        purchaseMap.getPrice(),
                        purchaseMap.getPower(),
                        purchaseMap.getGrenadeType());
                yield PurchaseFactory.createPurchase(purchaseMap.getPurchaseId(), client, handGrenade);
            }
            case ("Nuke") -> {
                RecreationalMcNuke nuke = new RecreationalMcNuke(purchaseMap.getSerialNumber(),
                        purchaseMap.getManufacturer(),
                        purchaseMap.getWeaponName(),
                        purchaseMap.getPrice(),
                        purchaseMap.getPower());
                yield PurchaseFactory.createPurchase(purchaseMap.getPurchaseId(), client, nuke);
            }
            default -> new Purchase();
        };
    }
}
