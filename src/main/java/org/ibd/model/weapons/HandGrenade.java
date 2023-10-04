package org.ibd.model.weapons;

import org.ibd.model.enums.GrenadeType;

public class HandGrenade extends Explosive{
    private GrenadeType type;

    public HandGrenade(Integer Id, String manufacturer, String name, Float price, Integer power, GrenadeType type) {
        super(Id, manufacturer, name, price, power);
        this.type = type;
    }

    public GrenadeType getType() {
        return type;
    }

    public void setType(GrenadeType type) {
        this.type = type;
    }
}