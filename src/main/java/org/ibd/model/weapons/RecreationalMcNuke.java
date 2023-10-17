package org.ibd.model.weapons;

import jakarta.persistence.*;

import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@DiscriminatorValue("McNuke:D")
@Access(AccessType.FIELD)
public class RecreationalMcNuke extends Explosive {
    public RecreationalMcNuke() {
    }


    public RecreationalMcNuke(Long serialNumber, String manufacturer, String name, BigDecimal price, Integer power) {
        super(serialNumber, manufacturer, name, price, power);
        setType("Nuke");

    }
}
