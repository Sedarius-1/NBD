package org.ibd.model.weapons;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.ibd.model.enums.GrenadeType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class WeaponMap {
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

    @NotNull
    @NotEmpty
    @BsonProperty("caliber")
    private String caliber;

    @NotNull
    @Min(0)
    @BsonProperty("length")
    private Float length;

    @NotNull
    @Min(0)
    @BsonProperty("power")
    private Integer power;

    @NotNull
    @BsonProperty("grenadetype")
    private GrenadeType grenadeType;

    public WeaponMap(@BsonProperty("serialNumber") Long serialNumber,
                     @BsonProperty("manufacturer") String manufacturer,
                     @BsonProperty("weaponName") String name,
                     @BsonProperty("price") BigDecimal price,
                     @BsonProperty("type") String type,
                     @BsonProperty("power") Integer power,
                     @BsonProperty("grenadeType") GrenadeType grenadeType,
                     @BsonProperty("caliber") String caliber,
                     @BsonProperty("length") Float length) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.type = type;
        this.caliber = caliber;
        this.length = length;
        this.power = power;
        this.grenadeType = grenadeType;
    }

    @Override
    public String toString() {
        return "WeaponMap{" +
                "serialNumber=" + serialNumber +
                ", manufacturer='" + manufacturer + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", caliber='" + caliber + '\'' +
                ", length=" + length +
                ", power=" + power +
                ", grenadeType=" + grenadeType +
                '}';
    }
}
