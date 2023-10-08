package org.ibd.model.clients;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.ibd.model.purchases.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Client")
public class Client {
    public Client(Long clientId, String name, String surname, String address, LocalDate birth) {
        this.clientId = clientId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birth = birth;
        this.purchaseSet = new HashSet<>();
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    @Column(name = "surename")
    private String surname;
    @NotNull
    @NotEmpty
    @Column(name = "address")
    private String address;
    @NotNull
    @Column(name = "birth")
    private LocalDate birth;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    @Fetch(FetchMode.JOIN)
    private Set<Purchase> purchaseSet;

    public Client() {

    }

    public Long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Set<Purchase> getPurchaseSet() {
        return purchaseSet;
    }

    public void setPurchaseSet(Set<Purchase> purchaseSet) {
        this.purchaseSet = purchaseSet;
    }

    public Long getId() {
        return id;
    }
}
