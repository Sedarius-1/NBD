package org.cassandra.model;

import com.datastax.oss.driver.api.mapper.annotations.*;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import com.datastax.oss.driver.api.mapper.entity.naming.SetterStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cassandra.Consts;

@Entity(defaultKeyspace = Consts.defaultKeyspace)
@CqlName(Consts.weaponTable)
@HierarchyScanStrategy(scanAncestors = true,
        highestAncestor = Weapon.class,
        includeHighestAncestor = true)
@PropertyStrategy(mutable = true,
        getterStyle = GetterStyle.JAVABEANS,
        setterStyle = SetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.UPPER_CAMEL_CASE)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pistol extends Weapon{

    private Float caliber;


}
