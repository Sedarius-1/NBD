package org.ibd.redisrepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;


import java.io.*;

@Getter
@Setter
public class RedisRepositoryImpl implements RedisRepository, AutoCloseable {

    private String hashPrefix;
    private String index;
    private JedisPooled jedisPooled;
    private Class<Object> objectClass;
    private Jsonb jsonb;

    private ObjectMapper objectMapper;

    public RedisRepositoryImpl(String configPath) throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        this.jsonb = JsonbBuilder.create();
        initRedisConnection(configPath);

    }


    private void initRedisConnection(String configPath) throws IOException {
        jedisPooled = RedisConfig.prepareRedisConfig(configPath);
    }

    @Override
    public void cacheObject(Object object) throws Exception {

        String objectString;

        objectString = objectMapper.writeValueAsString(object);

        jedisPooled.jsonSet(hashPrefix + object.toString(), objectString);
    }
    @Override
    public Object findObjectInCache(Long id) throws Exception {

        try {
        } catch (JedisConnectionException jedisConnectionException){
            throw new Exception("Unable to connect");
        }

        Object foundObject = jedisPooled.jsonGet(hashPrefix + id
                .toString());

        String json = jsonb.toJson(foundObject);

        return objectMapper.readValue(json,
                objectClass);
    }
    @Override
    public boolean deleteObjectFromCache(Long id) throws JedisConnectionException{
        try{
            jedisPooled.del(hashPrefix + id.toString());
            return true;
        }
        catch(Exception ex){
            return false;
        }

    }

    public void setHashPrefix(String hashPrefix) {
        this.hashPrefix = hashPrefix;
    }
    public void setObjectClass(Class objectClass) {
        this.objectClass = objectClass;
    }
    public void setIndex(String index) {
        this.index = index;
    }

    public void cleanCashe() throws JedisConnectionException{
        jedisPooled.flushAll();
    }
    @Override
    public void close() {
        try {
            cleanCashe();
        } catch (JedisConnectionException e){
        }
        jedisPooled.close();
    }
}
