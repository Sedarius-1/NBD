package org.cassandra.repo;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.cassandra.Consts;
import org.cassandra.Enums;
import org.cassandra.dao.ClientDao;
import org.cassandra.model.Client;

public class ClientRepo implements GeneralRepo<Client>{

    private CqlSession currentSession;
    private ClientDao clientDao;

    public ClientRepo(CqlSession currentSession) {
        this.currentSession = currentSession;
        currentSession.execute("TRUNCATE "+Consts.defaultKeyspace+"."+Consts.clientTable+";");
        createTable();
        GunShopMapper mapper = new GunShopMapperBuilder(currentSession).build();
        this.clientDao = mapper.getClientDao(CqlIdentifier.fromCql(Consts.defaultKeyspace));
    }

    @Override
    public void createTable() {
        SimpleStatement createTable = SchemaBuilder
                .createTable(CqlIdentifier.fromCql(Consts.clientTable))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier
                        .fromCql("clientid"), DataTypes.BIGINT)
                .withColumn(CqlIdentifier
                        .fromCql("name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier
                        .fromCql("surname"), DataTypes.TEXT)
                .withColumn(CqlIdentifier
                        .fromCql("address"), DataTypes.TEXT)
                .withColumn(CqlIdentifier
                        .fromCql("birth"), DataTypes.DATE)
                .withColumn(CqlIdentifier
                        .fromCql("balance"), DataTypes.BIGINT)
                .build();
        currentSession.execute(createTable);
    }

    @Override
    public void insert(Client entity) {
        clientDao.insertClient(entity);
    }

    @Override
    public Client select(Long entityId, String type) {
        return clientDao.selectClient(entityId);
    }

    @Override
    public void delete(Client entity) {
        clientDao.deleteClient(entity);

    }

    @Override
    public void update(Client entity) {
        clientDao.updateClient(entity);
    }
}
