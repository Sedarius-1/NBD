package org.ibd.performanceTests;

import org.ibd.model.clients.Client;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class PerformanceTestRepository {

    @Benchmark
    public void mongoDBPerformanceTest(MongoPerformanceTestConfigurator mongoPerformanceTestConfigurator, Blackhole blackhole) throws Exception {
        for(int i=0;i<20;i++){
            Client client = mongoPerformanceTestConfigurator.getMongoClientRepository().get(mongoPerformanceTestConfigurator.getTestClient().getClientId());
            blackhole.consume(client);
        }
    }


    @Benchmark
    public void redisDBPerformanceTest(RedisPerformanceTestConfigurator redisPerformanceTestConfigurator, Blackhole blackhole) throws Exception {
        for(int i=0;i<20;i++){
            Client client = redisPerformanceTestConfigurator.getRedisClientRepository().get(redisPerformanceTestConfigurator.getTestClient().getClientId());
            blackhole.consume(client);
        }

    }
}
