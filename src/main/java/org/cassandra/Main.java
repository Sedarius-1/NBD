package org.cassandra;

import org.cassandra.model.*;
import org.cassandra.repo.GeneralRepo;
import org.cassandra.repo.SessionController;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Client client = new Client(
                1L,
                "jan",
                "kowalski",
                "tutaj",
                LocalDate.now(),
                200L);

        Pistol pistol = new Pistol(2L, "u","w",200L,"Pistol", 21.0F);
        Rifle rifle = new Rifle(3L, "u","w",200L,"Rifle", 21.0F);

        Purchase purchase = new Purchase(4L, client.getClientid(), pistol.getSerialnumber());

        try (SessionController sessionController = new SessionController()) {

            GeneralRepo<Client> clientRepo =
                    sessionController.createClassRepository(Enums.accessType.CLIENT);

            GeneralRepo<Weapon> weaponRepo =
                    sessionController.createClassRepository(Enums.accessType.WEAPON);

            GeneralRepo<Purchase> purchaseRepo =
                    sessionController.createClassRepository(Enums.accessType.PURCHASE);

            clientRepo.insert(client);

            Client selectedClient = clientRepo.select(client.getClientid(),
                    client.getClass().getSimpleName());

            client.setName("Maciek");
            clientRepo.update(client);

            Client selectedClient2 = clientRepo.select(client.getClientid(),
                    client.getClass().getSimpleName());

            System.out.println(selectedClient2);
            clientRepo.delete(client);


            weaponRepo.insert(pistol);
            weaponRepo.insert(rifle);
            Pistol selectedWeapon = (Pistol) weaponRepo.select(pistol.getSerialnumber(),
                    pistol.getClass().getSimpleName());

            pistol.setName("Maciek");
            rifle.setName("arek");
            weaponRepo.update(pistol);
            weaponRepo.update(rifle);

            Pistol selectedWeapon2 = (Pistol) weaponRepo.select(pistol.getSerialnumber(),
                    pistol.getClass().getSimpleName());

            Rifle selectedWeapon3 = (Rifle) weaponRepo.select(rifle.getSerialnumber(),
                    rifle.getClass().getSimpleName());

            purchaseRepo.insert(purchase);
            purchase.setWeaponid(rifle.getSerialnumber());
            purchaseRepo.update(purchase);
            Purchase selectedPurchase = purchaseRepo.select(purchase.getPurchaseid(), purchase.getClass().getSimpleName());

            System.out.println(selectedClient2);
            clientRepo.delete(client);
            System.out.println(selectedWeapon2);
            weaponRepo.delete(pistol);
            System.out.println(selectedWeapon3);
            weaponRepo.delete(rifle);
            System.out.println(selectedPurchase);
            purchaseRepo.delete(selectedPurchase);
        } catch (Exception e) {

        throw new RuntimeException(e);
    }
}
}
