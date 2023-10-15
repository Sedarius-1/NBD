package org.ibd.factory;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.weapons.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;


@SuppressWarnings("unchecked")
@NoArgsConstructor
public class WeaponFactory {
    protected static final Logger logger = (Logger) LogManager.getLogger();

    public static <T extends Weapon> T manufactureWeapon(WeaponTypeEnum weaponTypeEnum, Map<String, String> params) {
        if (
            !params.containsKey("serialNumber") ||
            !params.containsKey("manufacturer") ||
            !params.containsKey("name") ||
            !params.containsKey("price")
        ) {
            logger.error("Missing one of required parameters for each type of weapons!");
            return null;
        }
        Long serialNumber;
        try {
            serialNumber = Long.valueOf(params.get("serialNumber"));
        } catch (Exception ex) {
            logger.error(ex.toString());
            return null;
        }
        String manufacturer = params.get("manufacturer");
        String name = params.get("name");
        BigDecimal price;
        try {
            price = new BigDecimal(params.get("price"));
        } catch (Exception ex) {
            logger.error(ex.toString());
            return null;
        }

        switch (weaponTypeEnum) {
            case RIFLE -> {
                if (
                    !params.containsKey("caliber") ||
                    !params.containsKey("length")
                ) {
                    logger.error("Missing one of parameters for rifle");
                    return null;
                }
                String caliber = params.get("caliber");
                try {
                    Float length = Float.valueOf(params.get("length"));
                    return (T) new Rifle(serialNumber, manufacturer, name, price, caliber, length);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    return null;
                }
            }
            case PISTOL -> {
                if (!params.containsKey("caliber")) {
                    logger.error("Missing caliber parameter for pistol");
                    return null;
                }
                String caliber = params.get("caliber");
                return (T) new Pistol(serialNumber, manufacturer, name, price, caliber);
            }
            case MCNUKE -> {
                if (!params.containsKey("power")) {
                    logger.error("Missing power parameter for mcNuke");
                    return null;
                }
                try {
                    Integer power = Integer.valueOf(params.get("power"));
                    return (T) new RecreationalMcNuke(serialNumber, manufacturer, name, price, power);
                } catch (Exception ex) {
                    logger.error(ex.toString());
                    return null;
                }
            }
            case HANDGRENADE -> {
                if (
                        !params.containsKey("grenadeType") ||
                        !params.containsKey("power")
                ) {
                    logger.error("Missing one of parameters for hand grenade");
                    return null;
                }
                try {
                    Integer power = Integer.valueOf(params.get("power"));
                    GrenadeType grenadeType = GrenadeType.valueOf(params.get("grenadeType"));
                    return (T) new HandGrenade(serialNumber, manufacturer, name, price, power, grenadeType);
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
