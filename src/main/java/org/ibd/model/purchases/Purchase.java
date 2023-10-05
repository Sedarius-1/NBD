package org.ibd.model.purchases;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.ibd.model.weapons.Weapon;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @NotEmpty
    private Long client;
    @NotNull
    @OneToOne(mappedBy = "Id")
    private Weapon weapon;
    @NotNull
    @Min(0)
    private Float price;
}
