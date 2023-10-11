package org.ibd.model.weapons;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("pIsToL")
@Access(AccessType.FIELD)
public class Pistol extends Firearm {
    public Pistol() {
    }

    public Pistol(Long serialNumber, String manufacturer, String name, Float price, String caliber) {
        super(serialNumber, manufacturer, name, price, caliber);
        setType("Pistol");
    }
}
