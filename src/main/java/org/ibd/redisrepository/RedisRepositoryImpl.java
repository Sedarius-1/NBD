package org.ibd.redisrepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import lombok.Getter;
import lombok.Setter;
import org.ibd.exceptions.NotInCacheException;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;


import java.io.*;
import java.util.Objects;

@Getter
@Setter
public class RedisRepositoryImpl implements RedisRepository, AutoCloseable {

    private String hashPrefix;
    private String index;
    private JedisPooled jedisPooled;
    private Class objectClass;
    private Jsonb jsonb;

    private ObjectMapper objectMapper;

    public RedisRepositoryImpl(String configPath, String hashPrefix, String index, Class objectClass) throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        this.jsonb = JsonbBuilder.create();
        this.hashPrefix = hashPrefix;
        this.objectClass = objectClass;
        this.index = index;
        initRedisConnection(configPath);
        cleanCache();

    }


    private void initRedisConnection(String configPath) throws IOException {
        jedisPooled = RedisConfig.prepareRedisConfig(configPath);

    }

    @Override
    public void cacheObject(Object object, Long id) throws Exception {

        String objectString;

        objectString = objectMapper.writeValueAsString(object);

        jedisPooled.jsonSet(hashPrefix + id, objectString);
        jedisPooled.expire(hashPrefix + id, 15*60);
    }

    @Override
    public Object findObjectInCache(Long id) throws Exception {


        Object foundObject = jedisPooled.jsonGet(hashPrefix + id
                .toString());
        if (Objects.isNull(foundObject)) {
            throw new NotInCacheException("Could not find object in cache");

        }
        String json = jsonb.toJson(foundObject);

        return objectMapper.readValue(json,
                objectClass);
    }

    @Override
    public void deleteObjectFromCache(Long id) {
        jedisPooled.del(hashPrefix + id.toString());
    }

    public void cleanCache() throws JedisConnectionException {
        jedisPooled.flushAll();
    }

    @Override
    public void close() {
        jedisPooled.close();
    }
}
