package org.cassandra.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.cassandra.Consts;
import org.cassandra.model.Purchase;

@Dao
public interface PurchaseDao {

    @Insert
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void insertPurchase(Purchase purchase);

    @Select
    @StatementAttributes(consistencyLevel = Consts.readConsistencyLevel)
    Purchase selectPurchase(Long entityId);

    @Delete
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void deletePurchase(Purchase purchase);

    @Update
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void updatePurchase(Purchase purchase);
}
