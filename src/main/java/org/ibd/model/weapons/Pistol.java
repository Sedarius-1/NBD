package org.ibd.model.weapons;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("pIsToL")
@Access(AccessType.FIELD)
public class Pistol extends Firearm {
    public Pistol() {
    }

    public Pistol(String manufacturer, String name, Float price, String caliber) {
        super(manufacturer, name, price, caliber);
    }
}
