package org.ibd.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bson.conversions.Bson;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.PurchaseFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.purchases.PurchaseMap;
import org.ibd.model.purchases.PurchaseMapper;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.PurchaseRepository;

import java.util.ArrayList;

public class PurchaseManager {
    private final PurchaseRepository purchaseRepository;

    protected static final Logger logger = (Logger) LogManager.getLogger();

    public PurchaseManager(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    //CREATE
    public Boolean registerPurchase(Long purchaseId, Client client, Weapon weapon) {
        try {
            Purchase purchase = PurchaseFactory.createPurchase(purchaseId, client, weapon);
            PurchaseMap purchaseMap = new PurchaseMap();
            PurchaseMapper.convertPurchaseToPurchaseMap(purchaseMap, purchase);
            purchaseRepository.add(purchaseMap);
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    //READ
    public Purchase getPurchase(Long purchaseId) {
        PurchaseMap purchaseMap = null;
        try {
            purchaseMap = purchaseRepository.get(purchaseId);

        } catch (RepositoryException e) {
            logger.error(e.toString());

        }
        return PurchaseMapper.convertPurchaseMapToPurchase(purchaseMap);
    }

    public ArrayList<Purchase> findPurchases(Bson finder){
        ArrayList<PurchaseMap> purchaseMapList;
        purchaseMapList = purchaseRepository.find(finder);
        ArrayList<Purchase> purchaseList = new ArrayList<>();
        purchaseMapList.forEach(purchaseMap -> {
            Purchase purchase = PurchaseMapper.convertPurchaseMapToPurchase(purchaseMap);
            purchaseList.add(purchase);
        });
        return purchaseList;
    }

    public ArrayList<Purchase> getAllPurchases(){
        ArrayList<PurchaseMap> purchaseMapList;
        purchaseMapList = purchaseRepository.getAll();
        ArrayList<Purchase> purchaseList = new ArrayList<>();
        purchaseMapList.forEach(purchaseMap -> {
            Purchase purchase = PurchaseMapper.convertPurchaseMapToPurchase(purchaseMap);
            purchaseList.add(purchase);
        });
        return purchaseList;
    }

    //Delete
    public Boolean unregisterPurchase(Long purchaseId) {
        try {
            purchaseRepository.remove(purchaseId);
        } catch (RepositoryException e) {
            logger.error(e.toString());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
