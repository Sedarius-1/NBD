package org.ibd.redisrepository.decoratedRepositories;

import org.ibd.model.clients.Client;
import org.ibd.model.weapons.*;
import org.ibd.redisrepository.RedisRepository;
import org.ibd.redisrepository.RedisRepositoryDecorated;
import org.ibd.redisrepository.RedisRepositoryImpl;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class WeaponDecorator extends RedisRepositoryDecorated {

    final String hashPrefix = "weapon:";
    final String index = "idx:weapon";

    public WeaponDecorator(RedisRepositoryImpl redisRepository) {
        super(redisRepository);
    }

    private void decorate(){
        redisRepositoryWrapper.setHashPrefix(hashPrefix);
        redisRepositoryWrapper.setIndex(index);
        redisRepositoryWrapper.setObjectClass(Weapon.class);
    }

    @Override
    public void cacheObject(Object object) throws Exception {
        decorate();

        String objectString;
        object = (Weapon)object;
        objectString = redisRepositoryWrapper.getObjectMapper().writeValueAsString(object);

        redisRepositoryWrapper.getJedisPooled().jsonSet(hashPrefix +((Weapon) object).getSerialNumber(), objectString);
    }
    @Override
    public Object findObjectInCache(Long id) throws Exception {
        decorate();
        try {
        } catch (JedisConnectionException jedisConnectionException){
            throw new Exception("Unable to connect");
        }

        Object foundObject = redisRepositoryWrapper.getJedisPooled().jsonGet(hashPrefix + id
                .toString());

        String json = redisRepositoryWrapper.getJsonb().toJson(foundObject);

        if(json.contains("Rifle")){
            redisRepositoryWrapper.setObjectClass(Rifle.class);
        }
        if(json.contains("Pistol")){
            redisRepositoryWrapper.setObjectClass(Pistol.class);
        }
        if(json.contains("Nuke")){
            redisRepositoryWrapper.setObjectClass(RecreationalMcNuke.class);
        }
        if(json.contains("HandGrenade")){
            redisRepositoryWrapper.setObjectClass(HandGrenade.class);
        }
        return redisRepositoryWrapper.getObjectMapper().readValue(json,
                redisRepositoryWrapper.getObjectClass());
    }

    @Override
    public boolean deleteObjectFromCache(Long id) {
        decorate();
        return super.deleteObjectFromCache(id);
    }
}
