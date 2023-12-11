package org.ibd.performanceTests;

import lombok.Getter;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.ibd.redisrepository.decoratedRepositories.CachedClientRepository;
import org.ibd.repository.ClientRepository;
import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@State(Scope.Thread)
public class RedisPerformanceTestConfigurator {

    ClientRepository redisClientRepository;
    private Client testClient;

    @Setup(Level.Invocation)
    public void setUp() throws RepositoryException {

        testClient = ClientFactory.createClient(
                1L,
                "TestName",
                "TestSurname",
                "TestAddress",
                LocalDate.now(),
                new BigDecimal("200.0")
        );

        redisClientRepository = new CachedClientRepository();

        redisClientRepository.add(testClient);
    }

    @TearDown(Level.Invocation)
    public void cleanUp(){
        redisClientRepository.close();
    }

}