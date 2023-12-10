package org.ibd.redisrepository.decoratedRepositories;


import org.ibd.exceptions.NotInCacheException;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.redisrepository.RedisRepositoryImpl;
import org.ibd.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CachedClientRepository extends ClientRepository {

    private RedisRepositoryImpl redisRepository;
    Logger log = LoggerFactory.getLogger("NBD");


    public CachedClientRepository() {
        super();
        try {
            redisRepository = new RedisRepositoryImpl("config.json", "client:", "idx:client", Client.class);
        } catch (Exception ex) {
            log.error("Could not establish connection to redis");
        }

    }

    //Create
    @Override
    public void add(Client client) throws RepositoryException {
        super.add(client);
        try {
            redisRepository.cacheObject(client, client.getClientId());
        } catch (Exception ex) {
            log.warn("Could not cache object");
        }
    }

    //Read
    @Override
    public Client get(Long id) throws RepositoryException {
        try {
            return (Client) redisRepository.findObjectInCache(id);
        } catch (NotInCacheException notInCacheException){
            log.debug("Item not in cache! Getting from database.");
            Client client = super.get(id);
            try {
                redisRepository.cacheObject(client, client.getClientId());
            } catch (Exception ex) {
                log.warn("Could not cache object");
            }
            return client;
        }catch (Exception ex) {
            log.warn(ex.toString());
        }
        return super.get(id);
    }


    //Update
    @Override
    public boolean updateOne(Long id, org.bson.conversions.Bson updater) {
        try{
            redisRepository.deleteObjectFromCache(id);
        } catch(Exception e){
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
        try{
            redisRepository.deleteObjectFromCache(id);
        } catch(Exception e){
            log.warn("Could not delete from cache");
        }
        super.remove(id);
    }


    @Override
    public void close() {
        try{
            redisRepository.close();
        }catch(Exception ex){
            log.error("Could not close redis connection successfully!");
        }
        super.close();
    }

}
