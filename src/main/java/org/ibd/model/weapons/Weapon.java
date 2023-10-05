package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotNull
    @NotEmpty
    private String manufacturer;
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Min(0)
    private Float price;

    public Weapon() {

    }

    public Weapon(String manufacturer, String name, Float price) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return Id;
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
