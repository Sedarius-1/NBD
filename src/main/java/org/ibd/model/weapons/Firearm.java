package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public abstract class Firearm extends Weapon {
    @NotNull
    @NotEmpty
    @Column(name = "caliber")
    private String caliber;

    public Firearm() {
    }

    public Firearm(Long serialNumber, String manufacturer, String name, Float price, String caliber) {
        super(serialNumber, manufacturer, name, price);
        this.caliber = caliber;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }
}
