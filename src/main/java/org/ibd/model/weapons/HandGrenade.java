package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.ibd.model.enums.GrenadeType;

@Entity
@DiscriminatorValue("hAnDgReNaDe")
@Access(AccessType.FIELD)
public class HandGrenade extends Explosive {
    @NotNull
    @Column(name = "type")
    private GrenadeType type;

    public HandGrenade() {
    }

    public HandGrenade(String manufacturer, String name, Float price, Integer power, GrenadeType type) {
        super(manufacturer, name, price, power);
        this.type = type;
    }

    public GrenadeType getType() {
        return type;
    }

    public void setType(GrenadeType type) {
        this.type = type;
    }
}