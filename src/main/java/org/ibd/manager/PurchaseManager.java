package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.PurchaseFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.PurchaseRepository;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;

    protected static final Logger logger = (Logger) LogManager.getLogger();

    public PurchaseManager(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Boolean registerPurchase(Long purchaseId, Client client, Weapon weapon) {
        try {
            purchaseRepository.add(PurchaseFactory.createPurchase(purchaseId, client, weapon));
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean undoPurchase(Purchase purchase) {
        try {
            purchaseRepository.remove(purchase);
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public BigDecimal calculateTotalSumOfClientPurchases(Client client) throws RepositoryException {
        List<Purchase> listOfPurchases = purchaseRepository.getAllPurchasesForSingleClient(client.getClientId());
        var ref = new Object() {
            BigDecimal totalSum = BigDecimal.ZERO;
        };
        listOfPurchases.forEach(purchase -> ref.totalSum = ref.totalSum.add(purchase.getWeapon().getPrice()));
        return ref.totalSum;
    }

    public List<Purchase> getAllPurchases() throws RepositoryException {
        return purchaseRepository.getAllPurchases();
    }

    public Purchase getPurchase(Long purchaseId) {
        Purchase purchase = null;
        try {
            purchase = purchaseRepository.get(purchaseId);
        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return purchase;
    }


}
