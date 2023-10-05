package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class Firearm extends Weapon {
    @NotNull
    @NotEmpty
    private String caliber;

    public Firearm() {
    }

    public Firearm(String manufacturer, String name, Float price, String caliber) {
        super(manufacturer, name, price);
        this.caliber = caliber;
    }

    public String getCaliber() {
        return caliber;
    }

    public void setCaliber(String caliber) {
        this.caliber = caliber;
    }
}
