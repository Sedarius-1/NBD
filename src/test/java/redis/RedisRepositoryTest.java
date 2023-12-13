package redis;

import org.ibd.exceptions.NotInCacheException;
import org.ibd.redisrepository.RedisRepositoryImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedisRepositoryTest {

    @Test
    void ClearCacheTest() {
        RedisRepositoryImpl redisRepository = assertDoesNotThrow(()->new RedisRepositoryImpl("config.json", "test", "idx:test", Integer.class));
        assertThrows(NotInCacheException.class,()->redisRepository.findObjectInCache(1L));
        assertThrows(NotInCacheException.class,()->redisRepository.findObjectInCache(2L));
        assertDoesNotThrow(()->redisRepository.cacheObject(1, 1L));
        assertDoesNotThrow(()->redisRepository.cacheObject(2, 2L));
        Integer test1 = (Integer) assertDoesNotThrow(()->redisRepository.findObjectInCache(1L));
        assertEquals(1, test1);
        Integer test2 = (Integer) assertDoesNotThrow(()->redisRepository.findObjectInCache(2L));
        assertEquals(2, test2);
        redisRepository.cleanCache();
        assertThrows(NotInCacheException.class,()->redisRepository.findObjectInCache(1L));
        assertThrows(NotInCacheException.class,()->redisRepository.findObjectInCache(2L));
    }
}
