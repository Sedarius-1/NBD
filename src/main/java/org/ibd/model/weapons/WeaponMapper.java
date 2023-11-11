package org.ibd.model.weapons;
import java.util.Objects;

public class WeaponMapper {

    public static Weapon convertWeaponMapToWeapon(WeaponMap weaponMap) {
        switch (weaponMap.getType()) {
            case ("Pistol"):
                return new Pistol(weaponMap.getSerialNumber(),
                        weaponMap.getManufacturer(),
                        weaponMap.getName(),
                        weaponMap.getPrice(),
                        weaponMap.getCaliber());
            case ("Rifle"):
                return new Rifle(weaponMap.getSerialNumber(),
                        weaponMap.getManufacturer(),
                        weaponMap.getName(),
                        weaponMap.getPrice(),
                        weaponMap.getCaliber(),
                        weaponMap.getLength());
            case ("HandGrenade"):
                return new HandGrenade(weaponMap.getSerialNumber(),
                        weaponMap.getManufacturer(),
                        weaponMap.getName(),
                        weaponMap.getPrice(),
                        weaponMap.getPower(),
                        weaponMap.getGrenadeType());
            case ("Nuke"):
                return new RecreationalMcNuke(weaponMap.getSerialNumber(),
                        weaponMap.getManufacturer(),
                        weaponMap.getName(),
                        weaponMap.getPrice(),
                        weaponMap.getPower());
        }
        return null;
    }

    public static WeaponMap convertWeaponToWeaponMap(WeaponMap weaponMap, Weapon weapon){
        weaponMap.setSerialNumber(weapon.getSerialNumber());
        weaponMap.setManufacturer(weapon.getManufacturer());
        weaponMap.setName(weapon.getName());
        weaponMap.setPrice(weapon.getPrice());
        weaponMap.setType(weapon.getType());
        if(Objects.equals(weaponMap.getType(), "Nuke")){
            RecreationalMcNuke nuke = (RecreationalMcNuke) weapon;
            weaponMap.setPower(nuke.getPower());
        }
        if(Objects.equals(weaponMap.getType(), "HandGrenade")){
            HandGrenade grenade = (HandGrenade) weapon;
            weaponMap.setPower(grenade.getPower());
            weaponMap.setGrenadeType(grenade.getGrenadeType());
        }
        if(Objects.equals(weaponMap.getType(), "Pistol")){
            Pistol pistol = (Pistol) weapon;
            weaponMap.setCaliber(pistol.getCaliber());
        }
        if(Objects.equals(weaponMap.getType(), "Rifle")){
            Rifle rifle = (Rifle) weapon;
            weaponMap.setCaliber(rifle.getCaliber());
            weaponMap.setLength(rifle.getLength());
        }
        return weaponMap;
    }
}
