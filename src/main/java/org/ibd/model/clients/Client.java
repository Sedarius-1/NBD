package org.ibd.model.clients;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.ibd.model.purchases.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @Fetch(FetchMode.JOIN)
    private Set<Purchase> purchaseSet;

    public Client() {

    }

}
