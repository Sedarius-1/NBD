package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public abstract class Explosive extends Weapon {
    @NotNull
    @Min(0)
    @Column(name = "power")
    private Integer power;

    public Explosive() {
    }

    public Explosive(Long serialNumber, String manufacturer, String name, BigDecimal price, Integer power) {
        super(serialNumber, manufacturer, name, price);
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }
}
