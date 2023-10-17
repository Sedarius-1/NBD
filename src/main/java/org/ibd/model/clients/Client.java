package org.ibd.model.clients;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.ibd.model.purchases.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@NoArgsConstructor
@Table(name = "Client")
public class Client {
    public Client(Long clientId, String name, String surname, String address, LocalDate birth, BigDecimal balance) {
        this.clientId = clientId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birth = birth;
        this.balance = balance;
        this.purchaseSet = new HashSet<>();
    }

    @SuppressWarnings("unused")
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @NotEmpty
    @Column(name = "clientId")
    private Long clientId;

    @NotNull
    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @NotEmpty
    @Column(name = "surname")
    private String surname;

    @NotNull
    @NotEmpty
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "birth")
    private LocalDate birth;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn
    @Fetch(FetchMode.JOIN)
    private Set<Purchase> purchaseSet;


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setPurchaseSet(Set<Purchase> purchaseSet) {
        this.purchaseSet = purchaseSet;
    }
}
