package org.ibd.manager;

import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.WeaponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class WeaponManager {
    private final WeaponRepository weaponRepository;

    Logger log = LoggerFactory.getLogger("NBD");

    public WeaponManager(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    //Create
    public Boolean registerWeapon(WeaponTypeEnum weaponType, Map<String, String> params) {
        try {
            if (Objects.isNull(weaponType) || Objects.isNull(params)) throw new RepositoryException("Null passed!");
            weaponRepository.add(WeaponFactory.manufactureWeapon(weaponType, params));
        } catch (RepositoryException e) {
            log.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    //Read
    public Weapon getWeapon(Long serialNumber) {
        Weapon weapon = null;
        try {
            weapon = weaponRepository.get(serialNumber);
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return weapon;
    }

    public ArrayList<Weapon> getAllWeapons() {
        try {
            return weaponRepository.getAll();
        } catch (Exception ex) {
            log.error("SOMETHING WRONG");
            return null;
        }

    }

    public ArrayList<Weapon> findWeapons(Bson finder) {
        try {
            return weaponRepository.find(finder);
        } catch (Exception ex) {
            log.error("SOMETHING WRONG");
            return null;
        }
    }

    //Update (yes, we will assume everything aside from price is constant)
    public Boolean changePrice(Long serialNumber, BigDecimal price) {
        try {
            if (price != null) {
                return weaponRepository.updateOne(serialNumber, Updates.set("price", price));
            } else throw new RepositoryException("null passed");
        } catch (RepositoryException e) {
            log.error(e.toString());
        }
        return false;
    }

    //Delete
    public Boolean unregisterWeapon(Long serialNumber) {
        try {
            weaponRepository.remove(serialNumber);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


}
