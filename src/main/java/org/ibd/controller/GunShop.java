package org.ibd.controller;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import org.bson.conversions.Bson;
import org.ibd.enums.ClientParamEnum;
import org.ibd.enums.WeaponTypeEnum;
import org.ibd.exceptions.RepositoryException;
import org.ibd.manager.ClientManager;
import org.ibd.manager.PurchaseManager;
import org.ibd.manager.WeaponManager;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.*;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.WeaponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class GunShop {
    private ClientManager clientManager;
    private WeaponManager weaponManager;
    private PurchaseManager purchaseManager;
    Logger log = LoggerFactory.getLogger("NBD");

    public GunShop() {
        try {
            clientManager = new ClientManager(new ClientRepository());
            weaponManager = new WeaponManager(new WeaponRepository());
        } catch (Exception e) {
            log.error("FATAL ERROR CREATING GunShop INSTANCE! ABORTING!");
            System.exit(1);
        }
    }

    /*  CLIENT  */
    public String formatClientInfo(Client client) {
        return "Client(" + AnsiCodes.ANSI_RED + client.getClientId() + AnsiCodes.ANSI_RESET + "): \nName: " + AnsiCodes.ANSI_GREEN + client.getName() + AnsiCodes.ANSI_RESET
                + "\nSurname: " + AnsiCodes.ANSI_GREEN + client.getSurname() + AnsiCodes.ANSI_RESET
                + "\nAddress: " + AnsiCodes.ANSI_GREEN + client.getAddress() + AnsiCodes.ANSI_RESET
                + "\nBirth: " + AnsiCodes.ANSI_GREEN + client.getBirth().toString() + AnsiCodes.ANSI_RESET
                + "\nBalance: " + AnsiCodes.ANSI_GREEN + client.getBalance() + AnsiCodes.ANSI_RESET;
    }
    //Create

    /**
     * Method registering client in a database
     *
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

    //Read
    public Client getClient(Long clientId) {
        try {
            return clientManager.getClient(clientId);

        } catch (Exception ex) {
            System.out.println(AnsiCodes.ANSI_RED + "Could not find client!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public String getClientInfo(Long clientId) {
        try {
            Client client = getClient(clientId);
            return formatClientInfo(client);

        } catch (Exception ex) {
            return AnsiCodes.ANSI_RED + "Could not find client to print info!" + AnsiCodes.ANSI_RESET;
        }
    }

    public BigDecimal getClientBalance(Long clientId) {
        try {
            Client client = clientManager.getClient(clientId);
            return client.getBalance();
        } catch (Exception ex) {
            log.error(ex.toString());
            return null;
        }
    }

    public ArrayList<Client> getAllClients() {
        return clientManager.getAllClients();

    }

    public void getAllClientsInfo() {
        System.out.printf(AnsiCodes.ANSI_CYAN + "PRINTING ALL CLIENTS\n" + AnsiCodes.ANSI_RESET);
        getAllClients().forEach(client -> System.out.println(formatClientInfo(client)));
    }

    public ArrayList<Client> findClients(Bson finder) {
        return clientManager.findClients(finder);
    }

    public void findClientsInfo(Bson finder) {
        System.out.printf(AnsiCodes.ANSI_CYAN + "PRINTING FOUND CLIENTS (condition : " + finder.toString() + ")\n" + AnsiCodes.ANSI_RESET);
        findClients(finder).forEach(client -> System.out.println(formatClientInfo(client)));
    }

    //Update
    public void updateClient(ClientParamEnum clientParamEnum, Object param, Long clientId) {
        switch (clientParamEnum) {
            case NAME -> {
                String name = (String) param;
                clientManager.changeName(clientId, name);
            }
            case SURNAME -> {
                String surname = (String) param;
                clientManager.changeSurname(clientId, surname);
            }
            case ADDRESS -> {
                String address = (String) param;
                clientManager.changeAddress(clientId, address);
            }
            case BIRTH -> {
                try {
                    LocalDate birth = (LocalDate) param;
                    clientManager.changeBirth(clientId, birth);
                } catch (Exception ex) {
                    log.info("Wrong date!");
                }
            }
            case BALANCE -> {
                try {
                    BigDecimal balance = (BigDecimal) param;
                    clientManager.changeBalance(clientId, balance);
                } catch (Exception ex) {
                    log.info("Wrong balance!");
                }
            }
        }
    }

    //Delete
    public boolean deleteClient(Long clientId) {
        return clientManager.unregisterClient(clientId);
    }

    /* WEAPON */

    public String formatWeaponInfo(Weapon weapon) {
        String weaponData =  "Weapon(" + AnsiCodes.ANSI_RED + weapon.getSerialNumber() + AnsiCodes.ANSI_RESET
                + "): \nName: " + AnsiCodes.ANSI_GREEN + weapon.getName() + AnsiCodes.ANSI_RESET
                + "\nType: " + AnsiCodes.ANSI_GREEN + weapon.getType() + AnsiCodes.ANSI_RESET
                + "\nManufacturer: " + AnsiCodes.ANSI_GREEN + weapon.getManufacturer() + AnsiCodes.ANSI_RESET
                + "\nPrice: " + AnsiCodes.ANSI_GREEN + weapon.getPrice().toString() + AnsiCodes.ANSI_RESET;
        switch(weapon.getType()){
            case "Pistol":
                return weaponData
                + "\nCaliber: " + AnsiCodes.ANSI_GREEN + ((Pistol) weapon).getCaliber() + AnsiCodes.ANSI_RESET;
            case "Rifle":
                return weaponData
                + "\nCaliber: " + AnsiCodes.ANSI_GREEN + ((Rifle) weapon).getCaliber() + AnsiCodes.ANSI_RESET
                + "\nLength: " + AnsiCodes.ANSI_GREEN + ((Rifle) weapon).getLength() + AnsiCodes.ANSI_RESET;
            case "HandGrenade":
                return weaponData
                + "\nPower: " + AnsiCodes.ANSI_GREEN + ((HandGrenade) weapon).getPower() + AnsiCodes.ANSI_RESET
                + "\nGrenade Type: " + AnsiCodes.ANSI_GREEN + ((HandGrenade) weapon).getGrenadeType() + AnsiCodes.ANSI_RESET;
            case "Nuke":
                return weaponData
                + "\nPower: " + AnsiCodes.ANSI_GREEN + ((RecreationalMcNuke) weapon).getPower() + AnsiCodes.ANSI_RESET;
        }
        return weaponData;

    }

    //Create
    public Weapon registerWeapon(WeaponTypeEnum weaponTypeEnum, Map<String, String> paramsMap) {
        if (weaponManager.registerWeapon(weaponTypeEnum, paramsMap)) {
            System.out.println(AnsiCodes.ANSI_GREEN + "Registered weapon!" + AnsiCodes.ANSI_RESET);
            return weaponManager.getWeapon(Long.valueOf(paramsMap.get("serialNumber")));
        } else {
            System.out.println(AnsiCodes.ANSI_RED + "Could not register weapon!" + AnsiCodes.ANSI_RESET);
        }
        return null;
    }

    //Read
    public Weapon getWeapon(Long serialNumber) {
        try {
            return weaponManager.getWeapon(serialNumber);

        } catch (Exception ex) {
            System.out.println(AnsiCodes.ANSI_RED + "Could not find weapon!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public String getWeaponInfo(Long serialNumber) {
        try {
            Weapon weapon = getWeapon(serialNumber);
            return formatWeaponInfo(weapon);

        } catch (Exception ex) {
            return AnsiCodes.ANSI_RED + "Could not find weapon to print info!" + AnsiCodes.ANSI_RESET;
        }
    }

    public ArrayList<Weapon> getAllWeapons() {
        try {
            return weaponManager.getAllWeapons();
        } catch (Exception ex) {
             return null;
        }
    }

    public void getAllWeaponsInfo() {
        System.out.printf(AnsiCodes.ANSI_CYAN + "PRINTING ALL WEAPONS\n" + AnsiCodes.ANSI_RESET);
        getAllWeapons().forEach(weapon -> System.out.println(formatWeaponInfo(weapon)));
    }

    public ArrayList<Weapon> findWeapons(Bson finder) {
        return weaponManager.findWeapons(finder);
    }

    public void findWeaponsInfo(Bson finder) {
        System.out.printf(AnsiCodes.ANSI_CYAN + "PRINTING FOUND WEAPONS (condition : " + finder.toString() + ")\n" + AnsiCodes.ANSI_RESET);
        findWeapons(finder).forEach(weapon -> System.out.println(formatWeaponInfo(weapon)));
    }

    //Update
    public void updateWeapon(BigDecimal price, Long serialNumber) {
        weaponManager.changePrice(serialNumber, price);
    }
    //Delete
    public boolean deleteWeapon(Long serialNumber) {
        return weaponManager.unregisterWeapon(serialNumber);
    }

    /* PURCHASE */

    public String formatPurchaseInfo(Purchase purchase) {

        return "Purchase(" + AnsiCodes.ANSI_RED + purchase.getPurchaseId() + AnsiCodes.ANSI_RESET +"):\n"
               + formatClientInfo(purchase.getClient()) + "\n"
               + formatWeaponInfo(purchase.getWeapon());
    }

    //Create
    public void registerPurchase(Long purchaseId, Client client, Weapon weapon) {
        ClientSession clientSession = purchaseManager.getMongoClientSession();
        try {
            clientSession.startTransaction();
            client = clientManager.getClient(client.getClientId());
            weapon = weaponManager.getWeapon(weapon.getSerialNumber());
            if (client.getBalance().compareTo(weapon.getPrice()) < 0) {
                System.out.println(AnsiCodes.ANSI_RED + "Could not register purchase!" + AnsiCodes.ANSI_RESET);
            }
            if (purchaseManager.registerPurchase(purchaseId, client, weapon)) {
                clientManager.changeBalance(client.getClientId(), client.getBalance().subtract(weapon.getPrice()));
                System.out.println(AnsiCodes.ANSI_GREEN + "Registered purchase!" + AnsiCodes.ANSI_RESET);
            } else {
                System.out.println(AnsiCodes.ANSI_RED + "Could not register purchase!" + AnsiCodes.ANSI_RESET);
            }
            clientSession.commitTransaction();
        } catch (MongoCommandException e) {
            log.error(e.toString());
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }



    //Read
    public Purchase getPurchase(Long purchaseId) {
        try {
            return purchaseManager.getPurchase(purchaseId);

        } catch (Exception ex) {
            System.out.println(AnsiCodes.ANSI_RED + "Could not find purchase!" + AnsiCodes.ANSI_RESET);
            return null;
        }
    }

    public String getPurchaseInfo(Long purchaseId) {
        try {
            Purchase purchase = getPurchase(purchaseId);
            return formatPurchaseInfo(purchase);

        } catch (Exception ex) {
            return AnsiCodes.ANSI_RED + "Could not find purchase to print info!" + AnsiCodes.ANSI_RESET;
        }
    }

    public ArrayList<Purchase> getAllPurchases() {
        try {
            return purchaseManager.getAllPurchases();
        } catch (Exception ex) {
            return null;
        }
    }

    public void getAllPurchasesInfo() {
        System.out.printf(AnsiCodes.ANSI_CYAN + "PRINTING ALL PURCHASES\n" + AnsiCodes.ANSI_RESET);
        getAllPurchases().forEach(purchase -> System.out.println(formatPurchaseInfo(purchase)));
    }

    public ArrayList<Purchase> findPurchases(Bson finder) {
        return purchaseManager.findPurchases(finder);
    }

    public void findPurchasesInfo(Bson finder) {
        System.out.printf(AnsiCodes.ANSI_CYAN + "PRINTING FOUND PURCHASES (condition : " + finder.toString() + ")\n" + AnsiCodes.ANSI_RESET);
        findPurchases(finder).forEach(purchase -> System.out.println(formatPurchaseInfo(purchase)));
    }

    //Delete
    public boolean deletePurchase(Long purchaseId) {
        return purchaseManager.unregisterPurchase(purchaseId);
    }


}
