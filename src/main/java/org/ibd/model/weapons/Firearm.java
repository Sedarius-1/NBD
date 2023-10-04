package org.ibd.model.weapons;

public class Firearm extends Weapon{
    private String caliber;

    public Firearm(Integer Id, String manufacturer, String name, Float price, String caliber) {
        super(Id, manufacturer, name, price);
        this.caliber = caliber;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }
}
