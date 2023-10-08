package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("rIfEl")
@Access(AccessType.FIELD)
public class Rifle extends Firearm {
    @NotNull
    @Min(0)
    @Column(name = "length")
    private Float length;

    public Rifle() {
    }

    public Rifle(String manufacturer, String name, Float price, String caliber, Float length) {
        super(manufacturer, name, price, caliber);
        this.length = length;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }
}
