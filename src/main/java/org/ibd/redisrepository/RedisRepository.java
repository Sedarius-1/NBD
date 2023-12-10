package org.ibd.redisrepository;

import org.ibd.exceptions.NotInCacheException;

public interface RedisRepository {
    void cacheObject(Object object, Long id) throws Exception;
    Object findObjectInCache(Long id) throws Exception;
    void deleteObjectFromCache(Long id) throws NotInCacheException;

}
