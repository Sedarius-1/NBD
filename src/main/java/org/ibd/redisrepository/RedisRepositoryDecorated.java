package org.ibd.redisrepository;

public class RedisRepositoryDecorated
        implements RedisRepository {

    protected RedisRepositoryImpl redisRepositoryWrapper;

    public RedisRepositoryDecorated(RedisRepositoryImpl redisRepository) {
        this.redisRepositoryWrapper = redisRepository;
    }


    @Override
    public void cacheObject(Object object) throws Exception {
        redisRepositoryWrapper.cacheObject(object);
    }
    @Override
    public Object findObjectInCache(Long id) throws Exception {
        return redisRepositoryWrapper.findObjectInCache(id);
    }
    @Override
    public boolean deleteObjectFromCache(Long id) {
        return redisRepositoryWrapper.deleteObjectFromCache(id);
    }



}
