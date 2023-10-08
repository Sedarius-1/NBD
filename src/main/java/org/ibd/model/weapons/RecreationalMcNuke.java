package org.ibd.model.weapons;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("McNuke:D")
@Access(AccessType.FIELD)
public class RecreationalMcNuke extends Explosive {
    public RecreationalMcNuke() {
    }

    public RecreationalMcNuke(String manufacturer, String name, Float price, Integer power) {
        super(manufacturer, name, price, power);
    }
}
