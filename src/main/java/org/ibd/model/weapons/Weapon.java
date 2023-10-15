package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Weapon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class Weapon {
    //Todo: add sold boolean
    @SuppressWarnings("unused")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @NotEmpty
    @Column(name = "serialNumber")
    private Long serialNumber;

    @NotNull
    @NotEmpty
    @Column(name = "manufacturer")
    private String manufacturer;
    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Min(0)
    @Column(name = "price")
    private BigDecimal price;

    @Column(name ="type", insertable = false, updatable = false)
    private String type;

    public Weapon(Long serialNumber, String manufacturer, String name, BigDecimal price) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }
}
