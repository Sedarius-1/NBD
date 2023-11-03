package org.ibd.model.weapons;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.ibd.model.enums.GrenadeType;

import java.math.BigDecimal;


@Getter
@Setter
public class HandGrenade extends Explosive {
    @NotNull
    @BsonProperty("grenadetype")
    private GrenadeType grenadeType;

    @BsonCreator
    public HandGrenade(@BsonProperty("serialNumber")Long serialNumber,
                       @BsonProperty("manufacturer")String manufacturer,
                       @BsonProperty("name")String name,
                       @BsonProperty("price")BigDecimal price,
                       @BsonProperty("power")Integer power,
                       @BsonProperty("grenadeType")GrenadeType grenadeType) {
        super(serialNumber, manufacturer, name, price, power);
        setType("HandGrenade");
        this.grenadeType = grenadeType;

    }

    @Override
    public String toString() {
        return "HandGrenade{" +
                "serialNumber=" + getSerialNumber() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", power=" + getPower() +
                ", grenadeType=" + grenadeType.toString() +
                '}';
    }
}