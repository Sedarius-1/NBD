package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Weapon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

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
    private Float price;

    public Weapon() {

    }

    public Weapon(Long serialNumber, String manufacturer, String name, Float price) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
