package org.ibd.model.weapons;

public class Rifle extends Firearm {
    private Float length;

    public Rifle(Integer Id, String manufacturer, String name, Float price, String caliber, Float length) {
        super(Id, manufacturer, name, price, caliber);
        this.length = length;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }
}
