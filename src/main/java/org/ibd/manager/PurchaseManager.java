package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.repository.PurchaseRepository;

import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;

    protected static final Logger logger = (Logger) LogManager.getLogger();

    public PurchaseManager(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Boolean makePurchase(Long clientId, Long serialNumber) {
        logger.error("makePurchase not implemented!");
        return Boolean.FALSE;
    }

    public Boolean undoPurchase(Long purchaseId) {
        logger.error("undoPurchase not implemented!");
        return Boolean.FALSE;
    }

    public Double calculateClientExpenses(Client client) throws RepositoryException {
        List<Purchase> listOfPurchases = purchaseRepository.getAllPurchasesForSingleClient(client.getClientId());
        return listOfPurchases.stream().mapToDouble(purchase -> purchase.getWeapon().getPrice()).sum();

    }
}
