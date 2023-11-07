package org.ibd.factory;

import lombok.NoArgsConstructor;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Weapon;

public class PurchaseFactory {

    public static Purchase createPurchase(Long purchaseId, Client client, Weapon weapon) {
        return new Purchase(purchaseId, client, weapon);
    }
}
