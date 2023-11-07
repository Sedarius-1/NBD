package org.ibd.model.weapons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class RecreationalMcNuke extends Explosive {

    @BsonCreator
    public RecreationalMcNuke(@BsonProperty("serialNumber") Long serialNumber,
                              @BsonProperty("manufacturer") String manufacturer,
                              @BsonProperty("name") String name,
                              @BsonProperty("price") BigDecimal price,
                              @BsonProperty("power") Integer power) {
        super(serialNumber, manufacturer, name, price, power);
        setType("Nuke");
    }

    @Override
    public String toString() {
        return "RecreationalMcNuke{" +
                "serialNumber=" + getSerialNumber() +
                ", manufacturer='" + getManufacturer() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", power=" + getPower() +
                '}';
    }
}
