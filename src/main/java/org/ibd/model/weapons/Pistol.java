package org.ibd.model.weapons;

import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;

@Setter
@Getter
public class Pistol extends Firearm {

    @BsonCreator
    public Pistol(@BsonProperty("serialNumber") Long serialNumber,
                  @BsonProperty("manufacturer") String manufacturer,
                  @BsonProperty("name") String name,
                  @BsonProperty("price") BigDecimal price,
                  @BsonProperty("caliber") String caliber) {
        super(serialNumber, manufacturer, name, price, caliber);
        setType("Pistol");
    }

    @Override
    public String toString() {
        return "Pistol{" +
                "serialNumber=" + getSerialNumber() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", caliber='" + getCaliber() + '\'' +
                '}';
    }
}
