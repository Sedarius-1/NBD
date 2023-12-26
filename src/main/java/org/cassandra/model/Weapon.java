package org.cassandra.model;


import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Weapon {

    @ClusteringColumn
    private Long serialNumber;
    private String manufacturer;
    private String name;
    private BigDecimal price;
    @PartitionKey
    private String type;

}
