package org.ibd.model.clients;

import org.ibd.model.purchases.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Client {
    private Integer id;
    private String name;
    private String surname;
    private String address;
    private LocalDate birth;
    private Set<Purchase> purchaseSet;
}
