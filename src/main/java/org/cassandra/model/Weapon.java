package org.cassandra.model;


import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Weapon {

    @ClusteringColumn
    private Long serialnumber;
    private String manufacturer;
    private String name;
    private Long price;
    @PartitionKey
    private String type;

}
