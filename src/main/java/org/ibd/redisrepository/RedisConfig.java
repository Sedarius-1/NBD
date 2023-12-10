package org.ibd.redisrepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.*;

import java.io.File;
import java.io.IOException;

public class RedisConfig {
    private static RedisConfigFile readConfigFile(String configPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(configPath), RedisConfigFile.class);
    }

    public static JedisPooled prepareRedisConfig(String configPath) throws IOException {
        RedisConfigFile redisConfigFile = readConfigFile(configPath);
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        return new JedisPooled(new HostAndPort(
                redisConfigFile.getAddress(),
                Integer.parseInt(redisConfigFile.getPort())),
                clientConfig);
    }
}
