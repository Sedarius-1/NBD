package org.ibd.model.purchases;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Weapon;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@OptimisticLocking(type = OptimisticLockType.NONE)
@Table(name = "Purchase")
public class Purchase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotEmpty
    @Column(name = "purchaseId")
    private Long purchaseId;
    @NotNull
    @NotEmpty
    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Weapon weapon;

    public Purchase(Long purchaseId, Client client, Weapon weapon) {
        this.purchaseId = purchaseId;
        this.client = client;
        this.weapon = weapon;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


}
