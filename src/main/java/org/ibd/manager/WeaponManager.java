//package org.ibd.manager;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.Logger;
//import org.ibd.enums.WeaponTypeEnum;
//import org.ibd.exceptions.RepositoryException;
//import org.ibd.factory.WeaponFactory;
//import org.ibd.model.weapons.Weapon;
//import org.ibd.repository.WeaponRepository;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//public class WeaponManager {
//    private final WeaponRepository weaponRepository;
//
//    protected static final Logger logger = (Logger) LogManager.getLogger();
//
//    public WeaponManager(WeaponRepository weaponRepository) {
//        this.weaponRepository = weaponRepository;
//    }
//
//    public Boolean registerWeapon(WeaponTypeEnum weaponType, Map<String, String> params) {
//        // TODO: might make this more user-friendly
//        try {
//            if(Objects.isNull(weaponType) || Objects.isNull(params)) throw new RepositoryException("Null passed!");
//            weaponRepository.add(WeaponFactory.manufactureWeapon(weaponType, params));
//        } catch (RepositoryException e) {
//            logger.error(e.toString());
//            return Boolean.FALSE;
//        }
//        return Boolean.TRUE;
//    }
//
//    public Boolean unregisterWeapon(Long serialNumber) {
//        try {
//            weaponRepository.remove(weaponRepository.get(serialNumber));
//        } catch (RepositoryException e) {
//            logger.error(e.toString());
//            return Boolean.FALSE;
//        }
//        return Boolean.TRUE;
//    }
//
//    public Weapon getWeapon(Long serialNumber){
//        Weapon weapon = null;
//        try {
//            weapon = weaponRepository.get(serialNumber);
//        } catch (RepositoryException e) {
//            logger.error(e.toString());
//
//        }
//        return weapon;
//    }
//
//    public List<Weapon> getAllWeapons() {
//        try{
//            return weaponRepository.getAllWeapons();
//        }
//        catch (Exception ex){
//            logger.error("SOMETHING WRONG");
//            return null;
//        }
//
//    }
//    // TODO: access functions
//}
