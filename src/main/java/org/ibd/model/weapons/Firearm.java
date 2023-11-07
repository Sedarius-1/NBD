package org.ibd.model.weapons;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public abstract class Firearm extends Weapon {
    @NotNull
    @NotEmpty
    @BsonProperty("caliber")
    private String caliber;


    @BsonCreator
    public Firearm(@BsonProperty("serialNumber") Long serialNumber,
                   @BsonProperty("manufacturer") String manufacturer,
                   @BsonProperty("name") String name,
                   @BsonProperty("price") BigDecimal price,
                   @BsonProperty("caliber") String caliber) {
        super(serialNumber, manufacturer, name, price);
        this.caliber = caliber;
    }


    @Override
    public String toString() {
        return "Firearm{" +
                "serialNumber=" + getSerialNumber() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", caliber='" + caliber + '\'' +
                '}';
    }
}
