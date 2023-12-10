package org.ibd.model.purchases;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.ibd.model.clients.Client;
import org.ibd.model.weapons.Weapon;

@Getter
@Setter
@NoArgsConstructor


public class Purchase {

    @BsonCreator
    public Purchase(@BsonProperty("purchaseId") Long purchaseId,
                    @BsonProperty("client") Client client,
                    @BsonProperty("weapon") Weapon weapon) {
        this.purchaseId = purchaseId;
        this.client = client;
        this.weapon = weapon;
    }

    @NotNull
    @NotEmpty
    @BsonProperty("purchaseId")
    private Long purchaseId;

    @NotNull
    @NotEmpty
    @BsonProperty("client")
    private Client client;

    @NotNull
    @BsonProperty("weapon")
    private Weapon weapon;


}
