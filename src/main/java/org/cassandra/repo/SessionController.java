package org.cassandra.repo;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import lombok.Getter;
import org.cassandra.Consts;
import org.cassandra.Enums;

import java.net.InetSocketAddress;

public class SessionController implements AutoCloseable {

    @Getter
    private static CqlSession currentSession;

    public SessionController() {

        initSession();
    }

    public void initSession() {

        currentSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("cassandra1", 9042))
                .addContactPoint(new InetSocketAddress("cassandra2", 9043))
                .addContactPoint(new InetSocketAddress("cassandra3", 9044))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("nbd",
                        "nbdpassword")
                .withKeyspace(Consts.defaultKeyspace)
                .build();

        SimpleStatement keyspace = SchemaBuilder
                .createKeyspace(CqlIdentifier.fromCql(Consts.defaultKeyspace))
                .ifNotExists()
                .withSimpleStrategy(3)
                .withDurableWrites(true)
                .build();
        currentSession.execute(keyspace);
    }

    @Override
    public void close() throws Exception {
        currentSession.close();
    }

    public GeneralRepo createClassRepository(Enums.accessType accessType) {

        return switch (accessType) {
            case CLIENT -> new ClientRepo(currentSession);
            case WEAPON -> new WeaponRepo(currentSession);
            case PURCHASE -> new PurchaseRepo(currentSession);
            case DEFAULT -> null;
        };
    }

    public CqlSession getCurrentSession(){
        return currentSession;
    }
}
