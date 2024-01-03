package org.cassandra.repo;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import org.cassandra.Consts;
import org.cassandra.dao.PurchaseDao;
import org.cassandra.model.Purchase;

import java.util.ArrayList;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;


public class PurchaseRepo implements GeneralRepo<Purchase> {
    private final CqlSession currentSession;
    private final PurchaseDao purchaseDao;

    public PurchaseRepo(CqlSession currentSession) {
        this.currentSession = currentSession;

        createTable();
        createTableByClient();
        createTableByWeapon();

        GunShopMapper mapper = new GunShopMapperBuilder(currentSession).build();
        this.purchaseDao = mapper.getPurchaseDao(CqlIdentifier.fromCql(Consts.defaultKeyspace));
    }

    @Override
    public void createTable() {
        SimpleStatement createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Consts.purchaseTable)).ifNotExists().withPartitionKey(CqlIdentifier.fromCql(Consts.purchaseTableId), DataTypes.BIGINT).withColumn(CqlIdentifier.fromCql(Consts.clientTableId), DataTypes.BIGINT).withColumn(CqlIdentifier.fromCql(Consts.weaponTableId), DataTypes.BIGINT)

                .build();
        currentSession.execute(createTable);
    }

    public void createTableByClient() {
        SimpleStatement createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Consts.purchaseTableByClient)).ifNotExists().withPartitionKey(CqlIdentifier.fromCql(Consts.clientTableId), DataTypes.BIGINT).withClusteringColumn(CqlIdentifier.fromCql(Consts.purchaseTableId), DataTypes.BIGINT).withColumn(CqlIdentifier.fromCql(Consts.weaponTableId), DataTypes.BIGINT)

                .build();
        currentSession.execute(createTable);
    }

    public void createTableByWeapon() {
        SimpleStatement createTable = SchemaBuilder.createTable(CqlIdentifier.fromCql(Consts.purchaseTableByWeapon)).ifNotExists().withPartitionKey(CqlIdentifier.fromCql(Consts.weaponTableId), DataTypes.BIGINT).withClusteringColumn(CqlIdentifier.fromCql(Consts.purchaseTableId), DataTypes.BIGINT).withColumn(CqlIdentifier.fromCql(Consts.clientTableId), DataTypes.BIGINT)

                .build();
        currentSession.execute(createTable);
    }

    @Override
    public void insert(Purchase entity) {
        purchaseDao.insertPurchase(entity);
        insertPurchaseIntoMergeTables(entity);
    }

    public void insertPurchaseIntoMergeTables(Purchase entity) {
        BatchStatement insertPurchaseIntoMergeTablesStatement = BatchStatement.builder(BatchType.LOGGED).addStatement(insertPurchaseByClient(entity)).addStatement(insertPurchaseByWeapon(entity)).build();
        currentSession.execute(insertPurchaseIntoMergeTablesStatement);
    }

    public SimpleStatement insertPurchaseByClient(Purchase entity) {
        return QueryBuilder.insertInto(Consts.purchaseTableByClient).value(Consts.clientTableId, literal(entity.getClientid())).value(Consts.purchaseTableId, literal(entity.getPurchaseid())).value(Consts.weaponTableId, literal(entity.getWeaponid())).build();
    }

    public SimpleStatement insertPurchaseByWeapon(Purchase entity) {
        return QueryBuilder.insertInto(Consts.purchaseTableByWeapon).value(Consts.weaponTableId, literal(entity.getWeaponid())).value(Consts.purchaseTableId, literal(entity.getPurchaseid())).value(Consts.clientTableId, literal(entity.getClientid())).build();
    }

    @Override
    public Purchase select(Long entityId, String type) {
        return purchaseDao.selectPurchase(entityId);
    }

    public ArrayList<Purchase> selectPurchasesByClientId(Long clientId) {
        SimpleStatement selectPurchasesByClientIdStatement = QueryBuilder.selectFrom(Consts.purchaseTableByClient).all().where(Relation.column(Consts.clientTableId).isEqualTo(literal(clientId))).build();

        try {
            ArrayList<Row> foundRecords = (ArrayList<Row>) currentSession.execute(selectPurchasesByClientIdStatement).all();

            ArrayList<Purchase> foundPurchases = new ArrayList<>();
            for (Row foundRecord : foundRecords) {
                foundPurchases.add(new Purchase(foundRecord.get(Consts.purchaseTableId, Long.class), foundRecord.get(Consts.clientTableId, Long.class), foundRecord.get(Consts.weaponTableId, Long.class)));
            }

            return foundPurchases;
        } catch (ClassCastException ex) {
            return new ArrayList<>();
        }
    }

    public ArrayList<Purchase> selectPurchasesByWeaponId(Long weaponId) {
        SimpleStatement selectPurchasesByClientIdStatement = QueryBuilder.selectFrom(Consts.purchaseTableByWeapon).all().where(Relation.column(Consts.weaponTableId).isEqualTo(literal(weaponId))).build();
        try {
            ArrayList<Row> foundRecords = (ArrayList<Row>) currentSession.execute(selectPurchasesByClientIdStatement).all();
            ArrayList<Purchase> foundPurchases = new ArrayList<>();
            for (Row foundRecord : foundRecords) {
                foundPurchases.add(new Purchase(foundRecord.get(Consts.purchaseTableId, Long.class), foundRecord.get(Consts.clientTableId, Long.class), foundRecord.get(Consts.weaponTableId, Long.class)));
            }
            return foundPurchases;
        } catch (ClassCastException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(Purchase entity) {
        purchaseDao.deletePurchase(entity);
        deletePurchaseFromMergeTables(entity);
    }

    public void deletePurchaseFromMergeTables(Purchase entity) {
        BatchStatement deletePurchaseFromMergeTablesStatement = BatchStatement.builder(BatchType.LOGGED).addStatement(deletePurchaseByClient(entity)).addStatement(deletePurchaseByWeapon(entity)).build();
        currentSession.execute(deletePurchaseFromMergeTablesStatement);
    }

    public SimpleStatement deletePurchaseByClient(Purchase entity) {
        return QueryBuilder.deleteFrom(Consts.purchaseTableByClient).where(Relation.column(Consts.clientTableId).isEqualTo(literal(entity.getClientid())), Relation.column(Consts.purchaseTableId).isEqualTo(literal(entity.getPurchaseid()))).build();
    }

    public SimpleStatement deletePurchaseByWeapon(Purchase entity) {
        return QueryBuilder.deleteFrom(Consts.purchaseTableByWeapon).where(Relation.column(Consts.weaponTableId).isEqualTo(literal(entity.getWeaponid())), Relation.column(Consts.purchaseTableId).isEqualTo(literal(entity.getPurchaseid()))).build();
    }

    @Override
    public void update(Purchase entity) {
        purchaseDao.updatePurchase(entity);
        updatePurchaseInMergeTables(entity);
    }


    public void updatePurchaseInMergeTables(Purchase entity) {
        BatchStatement updatePurchaseInMergeTablesStatement = BatchStatement.builder(BatchType.LOGGED).addStatement(updatePurchaseByClient(entity)).addStatement(updatePurchaseByWeapon(entity)).build();
        currentSession.execute(updatePurchaseInMergeTablesStatement);
    }

    public SimpleStatement updatePurchaseByClient(Purchase entity) {
        return QueryBuilder.update(Consts.purchaseTableByClient).setColumn(Consts.purchaseTableId, literal(entity.getPurchaseid())).setColumn(Consts.weaponTableId, literal(entity.getWeaponid())).where(Relation.column(Consts.clientTableId).isEqualTo(literal(entity.getClientid()))).build();
    }

    public SimpleStatement updatePurchaseByWeapon(Purchase entity) {
        return QueryBuilder.update(Consts.purchaseTableByWeapon).setColumn(Consts.purchaseTableId, literal(entity.getPurchaseid())).setColumn(Consts.clientTableId, literal(entity.getClientid())).where(Relation.column(Consts.weaponTableId).isEqualTo(literal(entity.getWeaponid()))).build();
    }
}
