package org.ibd.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.manager.ClientManager;
import org.ibd.manager.PurchaseManager;
import org.ibd.manager.WeaponManager;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.Rifle;
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NBDunit");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            PurchaseRepository purchaseRepository = new PurchaseRepository(entityManager);
            clientManager = new ClientManager(new ClientRepository(entityManager));
            weaponManager = new WeaponManager(new WeaponRepository(entityManager), purchaseRepository);
            purchaseManager = new PurchaseManager(purchaseRepository);
        } catch (Exception e) {
            logger.error("FATAL ERROR CREATING GunShop INSTANCE! ABORTING!");
            System.exit(1);
        }
    }

    public Client registerClient(Long clientId, String name, String surname, String address, LocalDate birth) {
        // clientId is temporary here
        if (clientManager.registerClient(clientId, name, surname, address, birth)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered client!" + AnsiCodes.ANSI_RESET);
            return clientManager.getClient(clientId);
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register client!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public <T extends Weapon> Object registerWeapon(WeaponTypeEnum weaponTypeEnum, Map<String, String> paramsMap) {
        // clientId is temporary here
        if (weaponManager.registerWeapon(weaponTypeEnum, paramsMap)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered weapon!" + AnsiCodes.ANSI_RESET);
            return weaponManager.getWeapon(Long.valueOf(paramsMap.get("serialNumber")));
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register weapon!" + AnsiCodes.ANSI_RESET);
        }
        return null;
    }

    public Purchase registerPurchase(Long purchaseId, Client client, Weapon weapon) {
        Purchase purchase = null;
        // clientId is temporary here
        if (purchaseManager.registerPurchase(purchaseId, client, weapon)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered purchase!" + AnsiCodes.ANSI_RESET);
            return purchaseManager.getPurchase(purchaseId);

        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register purchase!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public List<Weapon> getAvailableWeapons() {
        return weaponManager.getAllWeaponsForSale();
    }

}
