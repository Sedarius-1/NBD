package org.ibd.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.weapons.*;

import java.util.Map;


@SuppressWarnings("unchecked")
public class WeaponFactory {
    protected static final Logger logger = (Logger) LogManager.getLogger();

    public static <T extends Weapon> T manufactureWeapon(WeaponTypeEnum weaponTypeEnum, Map<String, String> params) {
        if (params.size() < 3) {
            logger.error("Too few arguments for any kind of weapon!");
            return null;
        }
        String manufacturer = params.get("manufacturer");
        String name = params.get("name");
        Float price;
        try {
            price = Float.valueOf(params.get("price"));
        } catch (Exception ex) {
            logger.error(ex.toString());
            return null;
        }

        switch (weaponTypeEnum) {
            case RIFLE -> {
                if (params.size() < 5) {
                    logger.error("Too few arguments for rifle!");
                    return null;
                }
                try {
                    String caliber = params.get("caliber");
                    Float length = Float.valueOf(params.get("length"));
                    return (T) new Rifle(manufacturer, name, price, caliber, length);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    return null;
                }
            }
            case PISTOL -> {
                if (params.size() < 4) {
                    logger.error("Too few arguments for pistol!");
                    return null;
                }
                try {
                    String caliber = params.get("caliber");
                    return (T) new Pistol(manufacturer, name, price, caliber);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    return null;
                }
            }
            case MCNUKE -> {
                if (params.size() < 4) {
                    logger.error("Too few arguments for mcnuke!");
                    return null;
                }
                try {
                    Integer power = Integer.valueOf(params.get("power"));
                    return (T) new RecreationalMcNuke(manufacturer, name, price, power);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    return null;
                }
            }
            case HANDGRENADE -> {
                if (params.size() < 5) {
                    logger.error("Too few arguments for hand grenade!");
                    return null;
                }
                try {
                    Integer power = Integer.valueOf(params.get("power"));
                    GrenadeType grenadeType = GrenadeType.valueOf(params.get("grenadeType"));
                    return (T) new HandGrenade(manufacturer, name, price, power, grenadeType);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    return null;
                }
            }
            default -> {
                logger.error("Unreachable! Enum fuckery");
                return null;
            }
        }
    }
}
