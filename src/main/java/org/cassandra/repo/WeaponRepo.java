package org.cassandra.repo;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import org.cassandra.Consts;
import org.cassandra.dao.WeaponDao;
import org.cassandra.model.Pistol;
import org.cassandra.model.Rifle;
import org.cassandra.model.Weapon;

public class WeaponRepo  implements GeneralRepo<Weapon>{

    private final CqlSession currentSession;
    private final WeaponDao weaponDao;

    public WeaponRepo(CqlSession currentSession) {
        this.currentSession = currentSession;
        currentSession.execute("TRUNCATE "+Consts.defaultKeyspace+"."+Consts.weaponTable+";");
        createTable();
        GunShopMapper mapper = new GunShopMapperBuilder(currentSession).build();
        this.weaponDao = mapper.getWeaponDao(CqlIdentifier.fromCql(Consts.defaultKeyspace));

    }

    @Override
    public void createTable() {
        SimpleStatement createTable = SchemaBuilder
                .createTable(CqlIdentifier.fromCql(Consts.weaponTable))
                .ifNotExists()
                .withPartitionKey(CqlIdentifier
                        .fromCql("type"), DataTypes.TEXT)
                .withClusteringColumn(CqlIdentifier
                        .fromCql("serialnumber"), DataTypes.BIGINT)
                .withColumn(CqlIdentifier
                        .fromCql("manufacturer"), DataTypes.TEXT)
                .withColumn(CqlIdentifier
                        .fromCql("name"), DataTypes.TEXT)
                .withColumn(CqlIdentifier
                        .fromCql("price"), DataTypes.BIGINT)
                .withColumn(CqlIdentifier
                        .fromCql("caliber"), DataTypes.FLOAT)
                .withColumn(CqlIdentifier
                        .fromCql("length"), DataTypes.FLOAT)
                .build();
        currentSession.execute(createTable);
    }

    @Override
    public void insert(Weapon entity) {
        if (entity.getClass().getSimpleName().equals("Pistol")) {
            weaponDao.insertPistol((Pistol) entity);
        } else {
            weaponDao.insertRifle((Rifle) entity);
        }
    }

    @Override
    public Weapon select(Long entityId, String type) {
        return switch (type){
            case "Pistol" -> weaponDao.selectPistol(
                    Pistol.class.getSimpleName(),
                    entityId);
            case "Rifle" -> weaponDao.selectRifle(
                    Rifle.class.getSimpleName(),
                    entityId);
            default -> null;
        };
    }

    @Override
    public void delete(Weapon entity) {
        if (entity.getClass().getSimpleName().equals("Pistol")) {
            weaponDao.deletePistol((Pistol) entity);
        } else {
            weaponDao.deleteRifle((Rifle) entity);
        }
    }

    @Override
    public void update(Weapon entity) {
        if (entity.getClass().getSimpleName().equals("Pistol")) {
            weaponDao.updatePistol((Pistol) entity);
        } else {
            weaponDao.updateRifle((Rifle) entity);
        }
    }
}
