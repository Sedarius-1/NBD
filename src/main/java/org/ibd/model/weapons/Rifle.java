package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@DiscriminatorValue("rIfEl")
@Access(AccessType.FIELD)
@Getter
@Setter
public class Rifle extends Firearm {
    @NotNull
    @Min(0)
    @Column(name = "length")
    private Float length;

    public Rifle() {
    }

    public Rifle(Long serialNumber, String manufacturer, String name, BigDecimal price, String caliber, Float length) {
        super(serialNumber, manufacturer, name, price, caliber);
        this.length = length;
        setType("Rifle");

    }

}
