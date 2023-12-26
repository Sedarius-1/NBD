package org.cassandra.repo;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.cassandra.dao.ClientDao;
import org.cassandra.dao.PurchaseDao;
import org.cassandra.dao.WeaponDao;

@Mapper
public interface GunShopMapper {

    @DaoFactory
    ClientDao getClientDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    WeaponDao getWeaponDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    PurchaseDao getPurchaseDao(@DaoKeyspace CqlIdentifier keyspace);


}
