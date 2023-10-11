//package org.ibd.controller;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.Logger;
//import org.ibd.repository.ClientRepository;
//import org.ibd.repository.PurchaseRepository;
//import org.ibd.repository.WeaponRepository;
//
//public class GunShop {
//    EntityManager entityManager;
//    public ClientRepository clientRepository; //TODO: privete this
//    PurchaseRepository purchaseRepository;
//    WeaponRepository weaponRepository;
//    protected static final Logger logger = (Logger) LogManager.getLogger();
//
//    public GunShop() {
//        try (EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.ibd")) {
//            entityManager = entityManagerFactory.createEntityManager();
//            clientRepository = new ClientRepository(entityManager);
//            purchaseRepository = new PurchaseRepository(entityManager);
//            weaponRepository = new WeaponRepository(entityManager);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//
//}
