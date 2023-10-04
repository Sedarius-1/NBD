package org.ibd.model.weapons;

public class Explosive extends Weapon{
    private Integer power;

    public Explosive(Integer Id, String manufacturer, String name, Float price, Integer power) {
        super(Id, manufacturer, name, price);
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
