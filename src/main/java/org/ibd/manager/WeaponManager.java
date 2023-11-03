package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.WeaponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeaponManager {
    private final WeaponRepository weaponRepository;

    Logger log = LoggerFactory.getLogger("NBD");

    public WeaponManager(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    public Boolean registerWeapon(WeaponTypeEnum weaponType, Map<String, String> params) {
        // TODO: might make this more user-friendly
        try {
            if (Objects.isNull(weaponType) || Objects.isNull(params)) throw new RepositoryException("Null passed!");
            weaponRepository.add(WeaponFactory.manufactureWeapon(weaponType, params));
        } catch (RepositoryException e) {
            log.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean unregisterWeapon(Long serialNumber) {
        try {
            weaponRepository.remove(serialNumber);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Weapon getWeapon(Long serialNumber) {
        Weapon weapon = null;
        try {
            weapon = weaponRepository.get(serialNumber);
        } catch (RepositoryException e) {
            log.error(e.toString());

        }
        return weapon;
    }

    public List<Weapon> getAllWeapons() {
        try {
            return weaponRepository.getAll();
        } catch (Exception ex) {
            log.error("SOMETHING WRONG");
            return null;
        }

    }

}
