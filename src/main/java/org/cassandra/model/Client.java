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
import java.time.LocalDate;
@Entity(defaultKeyspace = Consts.defaultKeyspace)
@CqlName(Consts.clientTable)
@PropertyStrategy(mutable = true,
        getterStyle = GetterStyle.JAVABEANS,
        setterStyle = SetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.EXACT_CASE)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @PartitionKey
    private Long clientid;
    private String name;
    private String surname;
    private String address;
    private LocalDate birth;
    private Long balance;


}
