package org.ibd.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.manager.ClientManager;
import org.ibd.manager.PurchaseManager;
import org.ibd.manager.WeaponManager;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;

import java.time.LocalDate;

public class GunShop {
    private ClientManager clientManager;
    private WeaponManager weaponManager;
    private PurchaseManager purchaseManager;

    protected static final Logger logger = (Logger) LogManager.getLogger();

    public GunShop() {
        try {
            // DO NOT MINDLESSLY ADD TRY-WITH-RESOURCES!!!!!
            // THIS FUCKER WAS THE SOURCE OF ALL OF OUR PROBLEMS
            // AT THE END OF TRY, MANAGER GOT DESTROYED
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            clientManager = new ClientManager(new ClientRepository(entityManager));
            weaponManager = new WeaponManager(new WeaponRepository(entityManager));
            purchaseManager = new PurchaseManager(new PurchaseRepository(entityManager));
        } catch (Exception e) {
            logger.error("FATAL ERROR CREATING GunShop INSTANCE! ABORTING!");
            System.exit(1);
        }
    }

    public void registerClient(Long clientId, String name, String surname, String address, LocalDate birth) {
        // clientId is temporary here
        if (clientManager.registerClient(clientId, name, surname, address, birth)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered client!" + AnsiCodes.ANSI_RESET);
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register client!" + AnsiCodes.ANSI_RESET);
        }
    }

}
