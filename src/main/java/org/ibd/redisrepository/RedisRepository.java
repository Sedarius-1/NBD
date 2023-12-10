package org.ibd.redisrepository;

import org.ibd.exceptions.NotInCacheException;

public interface RedisRepository {
    public void cacheObject(Object object, Long id) throws Exception;
    public Object findObjectInCache(Long id) throws Exception;
    public boolean deleteObjectFromCache(Long id) throws NotInCacheException;

}
