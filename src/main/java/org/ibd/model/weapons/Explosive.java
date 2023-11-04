package org.ibd.model.weapons;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class Explosive extends Weapon {
    @NotNull
    @Min(0)
    @BsonProperty("power")
    private Integer power;

    @BsonCreator
    public Explosive(@BsonProperty("serialNumber") Long serialNumber,
                     @BsonProperty("manufacturer") String manufacturer,
                     @BsonProperty("name") String name,
                     @BsonProperty("price") BigDecimal price,
                     @BsonProperty("power") Integer power) {
        super(serialNumber, manufacturer, name, price);
        this.power = power;
    }

    @Override
    public String toString() {
        return "Explosive{" +
                "serialNumber=" + getSerialNumber() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", power=" + power +
                '}';
    }
}
