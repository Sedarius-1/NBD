package org.ibd.redisrepository.decoratedRepositories;

import org.ibd.model.clients.Client;
import org.ibd.redisrepository.RedisRepositoryDecorated;
import org.ibd.redisrepository.RedisRepositoryImpl;

public class ClientDecorator extends RedisRepositoryDecorated {

    final String hashPrefix = "client:";
    final String index = "idx:client";

    public ClientDecorator(RedisRepositoryImpl redisRepository) {
        super(redisRepository);
    }

    private void decorate(){
        redisRepositoryWrapper.setHashPrefix(hashPrefix);
        redisRepositoryWrapper.setIndex(index);
        redisRepositoryWrapper.setObjectClass(Client.class);
    }

    @Override
    public void cacheObject(Object object) throws Exception {
        decorate();

        String objectString;
        object = (Client)object;
        objectString = redisRepositoryWrapper.getObjectMapper().writeValueAsString(object);

        redisRepositoryWrapper.getJedisPooled().jsonSet(hashPrefix +((Client) object).getClientId(), objectString);
    }
    @Override
    public Object findObjectInCache(Long id) throws Exception {
        decorate();
        return super.findObjectInCache(id);
    }
    @Override
    public boolean deleteObjectFromCache(Long id) {
        decorate();
        return super.deleteObjectFromCache(id);
    }
}
