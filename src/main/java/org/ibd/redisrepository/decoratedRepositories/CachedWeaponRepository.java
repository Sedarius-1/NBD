package org.ibd.redisrepository.decoratedRepositories;

import org.ibd.exceptions.NotInCacheException;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.weapons.WeaponMap;
import org.ibd.redisrepository.RedisRepositoryImpl;
import org.ibd.repository.WeaponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CachedWeaponRepository extends WeaponRepository {
    private RedisRepositoryImpl redisRepository;
    Logger log = LoggerFactory.getLogger("NBD");

    public CachedWeaponRepository() {
        super();
        try {
            redisRepository = new RedisRepositoryImpl("config.json", "weapon:", "idx:weapon", WeaponMap.class);
        } catch (Exception ex) {
            log.error("Could not establish connection to redis");
        }
    }

    @Override
    public void add(WeaponMap weaponMap) throws RepositoryException {
        super.add(weaponMap);
        try {
            redisRepository.cacheObject(weaponMap, weaponMap.getSerialNumber());
        } catch (Exception ex) {
            log.warn("Could not cache object");
        }
    }

    @Override
    public WeaponMap get(Long id) throws RepositoryException {
        try {
            return (WeaponMap) redisRepository.findObjectInCache(id);
        } catch (NotInCacheException notInCacheException) {
            log.debug("Item not in cache! Getting from database.");
            WeaponMap weaponMap = super.get(id);
            try {
                redisRepository.cacheObject(weaponMap, weaponMap.getSerialNumber());
            } catch (Exception ex) {
                log.warn("Could not cache object");
            }
            return weaponMap;
        } catch (Exception ex) {
            log.warn(ex.toString());
        }
        return super.get(id);
    }

    //Update
    @Override
    public boolean updateOne(Long id, org.bson.conversions.Bson updater) {
        try {
            redisRepository.deleteObjectFromCache(id);
        } catch (Exception e) {
            log.warn("Could not delete from cache");
        }
        boolean result = super.updateOne(id, updater);
        try {
            redisRepository.cacheObject(super.get(id), id);
        } catch (Exception ex) {
            log.warn("Could not cache object");
        }
        return result;
    }

    //Delete
    @Override
    public void remove(Long id) throws RepositoryException {
        try {
            redisRepository.deleteObjectFromCache(id);
        } catch (Exception e) {
            log.warn("Could not delete from cache");
        }
        super.remove(id);
    }

    @Override
    public void close() {
        try {
            redisRepository.close();
        } catch (Exception ex) {
            log.error("Could not close redis connection successfully!");
        }
        super.close();
    }
}
