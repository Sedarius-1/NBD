package org.ibd.model.weapons;
import java.util.Objects;

public class WeaponMapper {

    public static Weapon convertWeaponMapToWeapon(WeaponMap weaponMap) {
        return switch (weaponMap.getType()) {
            case ("Pistol") -> new Pistol(weaponMap.getSerialNumber(),
                    weaponMap.getManufacturer(),
                    weaponMap.getName(),
                    weaponMap.getPrice(),
                    weaponMap.getCaliber());
            case ("Rifle") -> new Rifle(weaponMap.getSerialNumber(),
                    weaponMap.getManufacturer(),
                    weaponMap.getName(),
                    weaponMap.getPrice(),
                    weaponMap.getCaliber(),
                    weaponMap.getLength());
            default -> null;
        };
    }

    public static void convertWeaponToWeaponMap(WeaponMap weaponMap, Weapon weapon){
        weaponMap.setSerialNumber(weapon.getSerialNumber());
        weaponMap.setManufacturer(weapon.getManufacturer());
        weaponMap.setName(weapon.getName());
        weaponMap.setPrice(weapon.getPrice());
        weaponMap.setType(weapon.getType());

        if(Objects.equals(weaponMap.getType(), "Pistol")){
            assert weapon instanceof Pistol;
            Pistol pistol = (Pistol) weapon;
            weaponMap.setCaliber(pistol.getCaliber());
        }
        if(Objects.equals(weaponMap.getType(), "Rifle")){
            assert weapon instanceof Rifle;
            Rifle rifle = (Rifle) weapon;
            weaponMap.setCaliber(rifle.getCaliber());
            weaponMap.setLength(rifle.getLength());
        }
    }
}
