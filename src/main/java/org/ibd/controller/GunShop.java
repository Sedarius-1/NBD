package org.ibd.controller;

import org.apache.logging.log4j.LogManager;
import org.ibd.enums.ClientParamEnum;
import org.ibd.manager.ClientManager;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GunShop {
    private ClientManager clientManager;
//    private WeaponManager weaponManager;
//    private PurchaseManager purchaseManager;
    Logger log = LoggerFactory.getLogger("NBD");

    public GunShop() {
        try {
            clientManager = new ClientManager(new ClientRepository());
        } catch (Exception e) {
            log.error("FATAL ERROR CREATING GunShop INSTANCE! ABORTING!");
            System.exit(1);
        }
    }

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

    public String getClientInfo(Long clientId) {
        try{
            Client client = clientManager.getClient(clientId);
            return
                     "Client: \nName: "   + AnsiCodes.ANSI_GREEN +client.getName() + AnsiCodes.ANSI_RESET
                            +"\nSurname: "+ AnsiCodes.ANSI_GREEN +client.getSurname()+ AnsiCodes.ANSI_RESET
                            +"\nAddress: "+ AnsiCodes.ANSI_GREEN +client.getAddress()+ AnsiCodes.ANSI_RESET
                            +"\nBirth: "  + AnsiCodes.ANSI_GREEN +client.getBirth().toString() + AnsiCodes.ANSI_RESET
                            +"\nBalance: "+ AnsiCodes.ANSI_GREEN +client.getBalance() + AnsiCodes.ANSI_RESET;
        }
        catch (Exception ex) {
            return AnsiCodes.ANSI_RED + "Could not find client!" + AnsiCodes.ANSI_RESET;
            // clientId is temporary here
        }
    }


//    public Weapon registerWeapon(WeaponTypeEnum weaponTypeEnum, Map<String, String> paramsMap) {
//        if (weaponManager.registerWeapon(weaponTypeEnum, paramsMap)) {
//            System.out.println(AnsiCodes.ANSI_GREEN + "Registered weapon!" + AnsiCodes.ANSI_RESET);
//            return weaponManager.getWeapon(Long.valueOf(paramsMap.get("serialNumber")));
//        } else {
//            System.out.println(AnsiCodes.ANSI_RED + "Could not register weapon!" + AnsiCodes.ANSI_RESET);
//        }
//        return null;
//    }
//
//    public Purchase registerPurchase(Long purchaseId, Client client, Weapon weapon) {
//        if (Objects.isNull(client)) {
//            System.out.println(AnsiCodes.ANSI_RED + "Client not provided!" + AnsiCodes.ANSI_RESET);
//            return null;
//        }
//        if (Objects.isNull(weapon)) {
//            System.out.println(AnsiCodes.ANSI_RED + "Weapon not provided!" + AnsiCodes.ANSI_RESET);
//            return null;
//        }
//        client = clientManager.getClient(client.getClientId());
//        if (client.getBalance().subtract(weapon.getPrice()).compareTo(BigDecimal.ZERO) < 0) {
//            System.out.println(AnsiCodes.ANSI_RED + "Insufficient funds!" + AnsiCodes.ANSI_RESET);
//            return null;
//        }
//        if (purchaseManager.registerPurchase(purchaseId, client, weapon)) {
//            if (clientManager.changeBalance(client.getClientId(), client.getBalance().subtract(weapon.getPrice()))) {
//                System.out.println(AnsiCodes.ANSI_GREEN + "Registered purchase!" + AnsiCodes.ANSI_RESET);
//                return purchaseManager.getPurchase(purchaseId);
//            } else {
//                purchaseManager.undoPurchase(purchaseManager.getPurchase(purchaseId));
//                System.out.println(AnsiCodes.ANSI_GREEN + "Purchase failed - could not save new balance" + AnsiCodes.ANSI_RESET);
//                return null;
//            }
//        } else {
//            System.out.println(AnsiCodes.ANSI_RED + "Could not register purchase!" + AnsiCodes.ANSI_RESET);
//            return null;
//        }
//    }
//
//    public List<Weapon> getAvailableWeapons() {
//        try {
//            List<Weapon> allWeapons = weaponManager.getAllWeapons();
//            List<Purchase> purchases = purchaseManager.getAllPurchases();
//            List<Weapon> soldWeapons = new ArrayList<>();
//            purchases.forEach(purchase -> soldWeapons.add(purchase.getWeapon()));
//            allWeapons.removeAll(soldWeapons);
//            return allWeapons;
//        } catch (Exception ex) {
//            logger.error(ex.toString());
//            return null;
//        }
//    }
//
//    public List<Purchase> getAllPurchases() {
//        try {
//            return purchaseManager.getAllPurchases();
//        } catch (Exception ex) {
//            logger.error(ex.toString());
//            return null;
//        }
//    }

    public BigDecimal getClientBalance(Long clientId) {
        try {
            Client client = clientManager.getClient(clientId);
            return client.getBalance();
        } catch (Exception ex) {
            log.error(ex.toString());
            return null;
        }
    }


    public Client modifyClient(ClientParamEnum clientParamEnum, Object param, Long clientId) {
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
                    return null;
                }
            }
            case BALANCE -> {
                try {
                    BigDecimal balance = (BigDecimal) param;
                    clientManager.changeBalance(clientId, balance);
                } catch (Exception ex) {
                    log.info("Wrong balance!");
                    return null;
                }
            }
        }
        return clientManager.getClient(clientId);
    }
}
