package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.factory.WeaponFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponManager {
    private final WeaponRepository weaponRepository;

    protected static final Logger logger = (Logger) LogManager.getLogger();

    public WeaponManager(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    public Boolean registerWeapon(WeaponTypeEnum weaponType, Map<String, String> params) {
        // TODO: might make this more user-friendly
        try {
            weaponRepository.addWeapon(WeaponFactory.manufactureWeapon(weaponType, params));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean unregisterWeapon(Long serialNumber) {
        try {
            weaponRepository.removeWeapon(weaponRepository.getWeapon(serialNumber));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Weapon getWeapon(Long serialNumber){
        Weapon weapon = null;
        try {
            weapon = weaponRepository.getWeapon(serialNumber);
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return weapon;
    }

    public List<Weapon> getAllWeapons() {
        try{
            return weaponRepository.getAllWeapons();
        }
        catch (Exception ex){
            logger.error("SOMETHING WRONG");
            return null;
        }

    }
    // TODO: access functions
}
