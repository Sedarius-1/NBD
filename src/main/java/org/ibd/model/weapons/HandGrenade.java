package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.ibd.model.enums.GrenadeType;

@Entity
@DiscriminatorValue("hAnDgReNaDe")
@Access(AccessType.FIELD)
public class HandGrenade extends Explosive {
    @NotNull
    @Column(name = "grenadetype")
    private GrenadeType grenadeType;

    public HandGrenade() {
    }

    public HandGrenade(Long serialNumber, String manufacturer, String name, Float price, Integer power, GrenadeType type) {
        super(serialNumber, manufacturer, name, price, power);
        this.grenadeType = type;
        setType("HandGrenade");
    }

    public GrenadeType getGrenadeType() {
        return grenadeType;
    }

    public void setType(GrenadeType type) {
        this.grenadeType = type;
    }
}