package org.ibd.manager;

import lombok.AllArgsConstructor;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.repository.PurchaseRepository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PurchaseManager {
    PurchaseRepository purchaseRepository;


    public Double calculateClientExpenses(Client client) throws RepositoryException {
        List<Purchase> listOfPurchases = purchaseRepository.getAllPurchasesForSingleClient(client.getClientId());
        return listOfPurchases.stream().mapToDouble(purchase -> purchase.getWeapon().getPrice()).sum();

    }
}
