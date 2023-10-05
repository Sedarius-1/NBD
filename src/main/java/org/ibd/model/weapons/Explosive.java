package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class Explosive extends Weapon {
    @NotNull
    @Min(0)
    private Integer power;

    public Explosive() {
    }

    public Explosive(String manufacturer, String name, Float price, Integer power) {
        super(manufacturer, name, price);
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
