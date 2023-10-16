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
import org.ibd.model.weapons.Weapon;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            weaponManager = new WeaponManager(new WeaponRepository(entityManager));
            purchaseManager = new PurchaseManager(purchaseRepository);
        } catch (Exception e) {
            logger.error("FATAL ERROR CREATING GunShop INSTANCE! ABORTING!");
            System.exit(1);
        }
    }

    /**
     * Method registering client in a database
     * @return an instance of {@link Client}, previously saved in a database
     */
    public Client registerClient(Long clientId, String name, String surname, String address, LocalDate birth, BigDecimal balance) {
        // clientId is temporary here
        if (clientManager.registerClient(clientId, name, surname, address, birth, balance)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered client!" + AnsiCodes.ANSI_RESET);
            return clientManager.getClient(clientId);
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register client!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public <T extends Weapon> Object registerWeapon(WeaponTypeEnum weaponTypeEnum, Map<String, String> paramsMap) {
        if (weaponManager.registerWeapon(weaponTypeEnum, paramsMap)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered weapon!" + AnsiCodes.ANSI_RESET);
            return weaponManager.getWeapon(Long.valueOf(paramsMap.get("serialNumber")));
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register weapon!" + AnsiCodes.ANSI_RESET);
        }
        return null;
    }

    public Purchase registerPurchase(Long purchaseId, Client client, Weapon weapon) {
        if( Objects.isNull(client)){
            System.out.println(AnsiCodes.ANSI_RED + "Client not provided!" + AnsiCodes.ANSI_RESET);
            return null;
        }
        if( Objects.isNull(weapon)){
            System.out.println(AnsiCodes.ANSI_RED + "Weapon not provided!" + AnsiCodes.ANSI_RESET);
            return null;
        }
        client = clientManager.getClient(client.getClientId());
        if(client.getBalance().subtract(weapon.getPrice()).compareTo(BigDecimal.ZERO)<0){
            System.out.println(AnsiCodes.ANSI_RED + "Insufficient funds!" + AnsiCodes.ANSI_RESET);
            return null;
        }
        if (purchaseManager.registerPurchase(purchaseId, client, weapon)) {
            if(clientManager.changeBalance(client.getClientId(), client.getBalance().subtract(weapon.getPrice()))){
                System.out.println(AnsiCodes.ANSI_GREEN + "Registered purchase!" + AnsiCodes.ANSI_RESET);
                return purchaseManager.getPurchase(purchaseId);
            }
            else{
                System.out.println(AnsiCodes.ANSI_GREEN + "Purchase failed - could not save new balance" + AnsiCodes.ANSI_RESET);
                return null;
            }
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register purchase!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public List<Weapon> getAvailableWeapons() {
        try{
            List<Weapon> allWeapons = weaponManager.getAllWeapons();
            List<Purchase> purchases = purchaseManager.getAllPurchases();
            List<Weapon> soldWeapons = new ArrayList<>();
            purchases.forEach(purchase -> soldWeapons.add(purchase.getWeapon()));
            allWeapons.removeAll(soldWeapons);
            return allWeapons;
        }
        catch (Exception ex){
            logger.error(ex.toString());
            return null;
        }
    }

    public List<Purchase> getAllPurchases() {
        try{
            return purchaseManager.getAllPurchases();
        }
        catch (Exception ex){
            logger.error(ex.toString());
            return null;
        }
    }
    public BigDecimal getClientBalance(Long clientId) {
        try{
            Client client = clientManager.getClient(clientId);
            return client.getBalance();
        }
        catch (Exception ex){
            logger.error(ex.toString());
            return null;
        }
    }

}
