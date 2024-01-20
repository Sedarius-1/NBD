package org.ibd.performanceTests;

import lombok.Getter;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@State(Scope.Thread)
public class MongoPerformanceTestConfigurator {
    ClientRepository mongoClientRepository;
    private Client testClient;

    @Setup(Level.Invocation)
    public void setUp() throws RepositoryException {

        testClient = new Client(
                1L,
                "TestName",
                "TestSurname",
                "TestAddress",
                LocalDate.now(),
                200.0F);

        mongoClientRepository = new ClientRepository();

        mongoClientRepository.add(testClient);
    }

    @TearDown(Level.Invocation)
    public void cleanUp(){
        mongoClientRepository.close();
    }

}