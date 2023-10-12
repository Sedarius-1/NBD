package org.ibd.model.weapons;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Weapon")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class Weapon {
    //Todo: add sold boolean
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
    private Float price;

    @Column(name ="type", insertable = false, updatable = false)
    private String type;

    public Weapon() {

    }

    public Weapon(Long serialNumber, String manufacturer, String name, Float price) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

}
