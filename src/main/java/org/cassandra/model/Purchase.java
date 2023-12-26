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

import java.math.BigDecimal;

@Entity(defaultKeyspace = Consts.defaultKeyspace)
@CqlName(Consts.purchaseTable)
@PropertyStrategy(mutable = true,
        getterStyle = GetterStyle.JAVABEANS,
        setterStyle = SetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Purchase {

    @PartitionKey
    private Long purchaseId;
    private Long clientId;
    private Long weaponId;

}
