package org.ibd.model.purchases;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.ibd.model.enums.GrenadeType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseMap {

    @NotNull
    @NotEmpty
    @BsonProperty("purchaseId")
    private Long purchaseId;

    @NotNull
    @NotEmpty
    @BsonProperty("clientId")
    private Long clientId;

    @NotNull
    @NotEmpty
    @BsonProperty("name")
    private String name;

    @NotNull
    @NotEmpty
    @BsonProperty("surname")
    private String surname;

    @NotNull
    @NotEmpty
    @BsonProperty("address")
    private String address;

    @NotNull
    @BsonProperty("birth")
    private LocalDate birth;

    @NotNull
    @BsonProperty("balance")
    private BigDecimal balance;

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
    @BsonProperty("weaponName")
    private String weaponName;

    @NotNull
    @Min(0)
    @BsonProperty("price")
    private BigDecimal price;

    @BsonProperty("type")
    private String type;

    @Min(0)
    @BsonProperty("power")
    private Integer power;

    @BsonProperty("grenadetype")
    private GrenadeType grenadeType;

    @NotEmpty
    @BsonProperty("caliber")
    private String caliber;

    @Min(0)
    @BsonProperty("length")
    private Float length;

    @BsonCreator
    public PurchaseMap(@BsonProperty("purchaseId") Long purchaseId,
                       @BsonProperty("clientId") Long clientId,
                       @BsonProperty("name") String name,
                       @BsonProperty("surname") String surname,
                       @BsonProperty("address") String address,
                       @BsonProperty("birth") LocalDate birth,
                       @BsonProperty("balance") BigDecimal balance,
                       @BsonProperty("serialNumber") Long serialNumber,
                       @BsonProperty("manufacturer") String manufacturer,
                       @BsonProperty("weaponName") String weaponName,
                       @BsonProperty("price") BigDecimal price,
                       @BsonProperty("type") String type,
                       @BsonProperty("power") Integer power,
                       @BsonProperty("grenadeType") GrenadeType grenadeType,
                       @BsonProperty("caliber") String caliber,
                       @BsonProperty("length") Float length) {
        this.purchaseId = purchaseId;
        this.clientId = clientId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birth = birth;
        this.balance = balance;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.weaponName = weaponName;
        this.price = price;
        this.type = type;
        this.power = power;
        this.grenadeType = grenadeType;
        this.caliber = caliber;
        this.length = length;
    }


    @Override
    public String toString() {
        return "PurchaseMap{" +
                "purchaseId=" + purchaseId +
                ", clientId=" + clientId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", birth=" + birth +
                ", balance=" + balance +
                ", serialNumber=" + serialNumber +
                ", manufacturer='" + manufacturer + '\'' +
                ", weaponName='" + weaponName + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", power=" + power +
                ", grenadeType=" + grenadeType +
                ", caliber='" + caliber + '\'' +
                ", length=" + length +
                '}';
    }
}
