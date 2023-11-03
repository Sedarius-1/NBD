package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;

@Getter
@Setter
public class Rifle extends Firearm {
    @NotNull
    @Min(0)
    @BsonProperty("length")
    private Float length;

    @BsonCreator
    public Rifle(@BsonProperty("serialNumber")Long serialNumber,
                 @BsonProperty("manufacturer")String manufacturer,
                 @BsonProperty("name")String name,
                 @BsonProperty("price")BigDecimal price,
                 @BsonProperty("caliber")String caliber,
                 @BsonProperty("length") Float length) {
        super(serialNumber, manufacturer, name, price, caliber);
        this.length = length;
        setType("Rifle");

    }
    @Override
    public String toString() {
        return "Rifle{" +
                "serialNumber=" + getSerialNumber() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", caliber='" + getCaliber() + '\'' +
                "length=" + length +
                '}';
    }
}
