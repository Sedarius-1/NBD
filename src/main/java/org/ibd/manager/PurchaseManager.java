package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.ClientFactory;
import org.ibd.factory.PurchaseFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.PurchaseRepository;

import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;

    protected static final Logger logger = (Logger) LogManager.getLogger();

    public PurchaseManager(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Boolean registerPurchase(Long purchaseId, Client client, Weapon weapon) {
        try {
            purchaseRepository.persistPurchase(PurchaseFactory.createPurchase(purchaseId, client, weapon));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean undoPurchase(Long purchaseId) {
        logger.error("undoPurchase not implemented!");
        return Boolean.FALSE;
    }

    public Double calculateClientExpenses(Client client) throws RepositoryException {
        List<Purchase> listOfPurchases = purchaseRepository.getAllPurchasesForSingleClient(client.getClientId());
        return listOfPurchases.stream().mapToDouble(purchase -> purchase.getWeapon().getPrice()).sum();

    }

    public List<Purchase> getAllPurchases() throws RepositoryException {
        return purchaseRepository.getAllPurchases();
    }

    public Purchase getPurchase(Long purchaseId) {
        Purchase purchase = null;
        try {
            purchase = purchaseRepository.getPurchase(purchaseId);
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return purchase;
    }


}
