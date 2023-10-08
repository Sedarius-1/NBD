package org.ibd.model.weapons;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("McNuke:D")
@Access(AccessType.FIELD)
public class RecreationalMcNuke extends Explosive {
    public RecreationalMcNuke() {
    }


    public RecreationalMcNuke(Long serialNumber, String manufacturer, String name, Float price, Integer power) {
        super(serialNumber, manufacturer, name, price, power);
    }
}
