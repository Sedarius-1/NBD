package org.ibd.model.weapons;

import jakarta.validation.constraints.Min;
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
public abstract class Weapon {

    @NotNull
    @NotEmpty
    @BsonProperty("serialNumber")
    private Long serialNumber;

    @NotNull
    @NotEmpty
    @BsonProperty("manufacturer")
    private String manufacturer;
    @NotNull
    @NotEmpty
    @BsonProperty("name")
    private String name;

    @NotNull
    @Min(0)
    @BsonProperty("price")
    private BigDecimal price;

    @BsonProperty("type")
    private String type;

    @BsonCreator
    public Weapon(@BsonProperty("serialNumber") Long serialNumber,
                  @BsonProperty("manufacturer") String manufacturer,
                  @BsonProperty("name") String name,
                  @BsonProperty("price") BigDecimal price) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.type = null;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "serialNumber=" + serialNumber +
                ", manufacturer='" + manufacturer + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
