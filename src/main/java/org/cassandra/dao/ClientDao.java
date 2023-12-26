package org.cassandra.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.cassandra.Consts;
import org.cassandra.model.Client;

@Dao

public interface ClientDao {

    @Insert
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void insertClient(Client client);

    @Select
    @StatementAttributes(consistencyLevel = Consts.readConsistencyLevel)
    Client selectClient(Long entityId);

    @Delete
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void deleteClient(Client client);

    @Update
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void updateClient(Client client);
}
