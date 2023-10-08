package org.ibd.model.purchases;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Weapon;

@Entity
@Table(name = "Purchase")
public class Purchase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "purchaseId")
    private Long purchaseId;
    @NotNull
    @NotEmpty
    @ManyToOne
    private Client client;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Weapon weapon;
    @NotNull
    @Min(0)
    @Column(name = "price")
    private Float price;
}
