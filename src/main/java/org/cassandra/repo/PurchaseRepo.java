package org.cassandra.repo;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.cassandra.Consts;
import org.cassandra.dao.PurchaseDao;
import org.cassandra.model.Purchase;


public class PurchaseRepo implements GeneralRepo<Purchase>{
    private final CqlSession currentSession;
    private final PurchaseDao purchaseDao;

    public PurchaseRepo(CqlSession currentSession) {
        this.currentSession = currentSession;

        createTable();
        GunShopMapper mapper = new GunShopMapperBuilder(currentSession).build();
        this.purchaseDao = mapper.getPurchaseDao(CqlIdentifier.fromCql(Consts.defaultKeyspace));
    }

    @Override
    public void createTable() {
        SimpleStatement createTable = SchemaBuilder
                .createTable(CqlIdentifier.fromCql(Consts.purchaseTable))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier
                        .fromCql("purchaseid"), DataTypes.BIGINT)
                .withColumn(CqlIdentifier
                        .fromCql("clientid"), DataTypes.BIGINT)
                .withColumn(CqlIdentifier
                        .fromCql("weaponid"), DataTypes.BIGINT)

                .build();
        currentSession.execute(createTable);
    }

    @Override
    public void insert(Purchase entity) {
        purchaseDao.insertPurchase(entity);
    }

    @Override
    public Purchase select(Long entityId, String type) {
        return purchaseDao.selectPurchase(entityId);
    }

    @Override
    public void delete(Purchase entity) {
        purchaseDao.deletePurchase(entity);

    }

    @Override
    public void update(Purchase entity) {
        purchaseDao.updatePurchase(entity);
    }
}
