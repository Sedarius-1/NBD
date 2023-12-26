package org.cassandra.dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import org.cassandra.Consts;
import org.cassandra.model.Rifle;
import org.cassandra.model.Pistol;

@Dao
public interface WeaponDao {

    @Insert
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void insertPistol(Pistol pistol);

    @Insert
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void insertRifle(Rifle rifle);

    @Select
    @StatementAttributes(consistencyLevel = Consts.readConsistencyLevel)
    Pistol selectPistol(String discriminator, Long entityId);

    @Select
    @StatementAttributes(consistencyLevel = Consts.readConsistencyLevel)
    Rifle selectRifle(String discriminator, Long entityId);

    @Delete
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void deletePistol(Pistol pistol);

    @Delete
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void deleteRifle(Rifle rifle);

    @Update
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void updatePistol(Pistol pistol);

    @Update
    @StatementAttributes(consistencyLevel = Consts.writeConsistencyLevel)
    void updateRifle(Rifle rifle);
}
