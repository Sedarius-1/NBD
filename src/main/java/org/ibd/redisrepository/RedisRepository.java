package org.ibd.redisrepository;

public interface RedisRepository {
    public void cacheObject(Object object) throws Exception;
    public Object findObjectInCache(Long id) throws Exception;
    public boolean deleteObjectFromCache(Long id);

}
