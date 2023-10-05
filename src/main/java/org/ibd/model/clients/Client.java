package org.ibd.model.clients;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.ibd.model.purchases.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String surname;
    @NotNull
    @NotEmpty
    private String address;
    @NotNull
    private LocalDate birth;
    @NotNull
    @OneToMany
    @JoinColumn(name = "Id")
    @Fetch(FetchMode.JOIN)
    private Set<Purchase> purchaseSet;
}
