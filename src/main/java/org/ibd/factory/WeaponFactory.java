package org.ibd.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.weapons.*;

import java.util.List;


public class WeaponFactory {
    protected static final Logger logger = (Logger) LogManager.getLogger();
    public Weapon manufactureWeapon(WeaponTypeEnum weaponTypeEnum, List<String> params){
        if(params.size()<4){
            logger.error("Too few arguments for any kind of weapon!");
            return null;
        }
        Integer id = null;
        String manufacturer = params.get(1);
        String name = params.get(2);
        Float price = null;
        try{
            id = Integer.valueOf(params.get(0));
            price = Float.valueOf(params.get(3));
        }
        catch (Exception ex){
            logger.error(ex.toString());
        }
        switch(weaponTypeEnum){
            case RIFLE -> {
                if(params.size()<6){
                    logger.error("Too few arguments for rifle!");
                    return null;
                }
                try{
                    String caliber = params.get(4);
                    Float length = Float.valueOf(params.get(5));
                    return new Rifle(id, manufacturer, name, price, caliber, length);
                }
                catch (Exception ex){
                    logger.error(ex.toString());
                }
            }
            case PISTOL -> {
                if(params.size()<5){
                    logger.error("Too few arguments for pistol!");
                    return null;
                }
                try{
                    String caliber = params.get(4);
                    return new Pistol(id, manufacturer, name, price, caliber);
                }
                catch (Exception ex){
                    logger.error(ex.toString());
                }
            }
            case MCNUKE -> {
                if(params.size()<5){
                    logger.error("Too few arguments for mcnuke!");
                    return null;
                }
                try{
                    Integer power = Integer.valueOf(params.get(4));
                    return new RecreationalMcNuke(id, manufacturer, name, price, power);
                }
                catch (Exception ex){
                    logger.error(ex.toString());
                }
            }
            case HANDGRENADE -> {
                if(params.size()<6){
                    logger.error("Too few arguments for hand grenade!");
                    return null;
                }
                try{
                    Integer power = Integer.valueOf(params.get(4));
                    GrenadeType grenadeType = GrenadeType.valueOf(params.get(5));
                    return new HandGrenade(id, manufacturer, name, price, power, grenadeType);
                }
                catch (Exception ex){
                    logger.error(ex.toString());
                }
            }
        }
        return null;
    }
}
