package org.ibd.model.clients;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor


public class Client implements Serializable {
    @BsonCreator
    public Client(@BsonProperty("clientId") Long clientId,
                  @BsonProperty("name") String name,
                  @BsonProperty("surname") String surname,
                  @BsonProperty("address") String address,
                  @BsonProperty("birth") LocalDate birth,
                  @BsonProperty("balance") BigDecimal balance) {
        this.clientId = clientId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birth = birth;
        this.balance = balance;
    }

    @NotNull
    @NotEmpty
    @BsonProperty("clientId")
    private Long clientId;

    @NotNull
    @NotEmpty
    @BsonProperty("name")
    private String name;

    @NotNull
    @NotEmpty
    @BsonProperty("surname")
    private String surname;

    @NotNull
    @NotEmpty
    @BsonProperty("address")
    private String address;

    @NotNull
    @BsonProperty("birth")
    private LocalDate birth;

    @NotNull
    @BsonProperty("balance")
    private BigDecimal balance;


    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", birth=" + birth +
                ", balance=" + balance +'}';
    }
}
