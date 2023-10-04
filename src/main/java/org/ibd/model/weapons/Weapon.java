package org.ibd.model.weapons;

public class Weapon {

    private final Integer Id;
    private String manufacturer;
    private String name;
    private Float price;

    public Weapon(Integer Id, String manufacturer, String name, Float price) {
        this.Id = Id;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
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
