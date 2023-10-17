package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.ibd.model.enums.GrenadeType;

import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@DiscriminatorValue("hAnDgReNaDe")
@Access(AccessType.FIELD)
public class HandGrenade extends Explosive {
    @NotNull
    @Column(name = "grenadetype")
    private GrenadeType grenadeType;

    public HandGrenade() {
    }

    public HandGrenade(Long serialNumber, String manufacturer, String name, BigDecimal price, Integer power, GrenadeType type) {
        super(serialNumber, manufacturer, name, price, power);
        this.grenadeType = type;
        setType("HandGrenade");
    }
}