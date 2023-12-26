package org.cassandra;

import org.cassandra.model.Client;
import org.cassandra.model.Pistol;
import org.cassandra.model.Rifle;
import org.cassandra.model.Weapon;
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


        try (SessionController sessionController = new SessionController()) {

            GeneralRepo<Client> clientRepository =
                    sessionController.createClassRepository(Enums.accessType.CLIENT);

            clientRepository.insert(client);

            Client selectedClient = clientRepository.select(client.getClientid(),
                    client.getClass().getSimpleName());

            client.setName("Maciek");
            clientRepository.update(client);

            Client selectedClient2 = clientRepository.select(client.getClientid(),
                    client.getClass().getSimpleName());

            System.out.println(selectedClient2);
            clientRepository.delete(client);



            GeneralRepo<Weapon> weaponRepo =
                    sessionController.createClassRepository(Enums.accessType.WEAPON);

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

            System.out.println(selectedClient2);
            clientRepository.delete(client);
            System.out.println(selectedWeapon2);
            weaponRepo.delete(pistol);
            System.out.println(selectedWeapon3);
            weaponRepo.delete(rifle);
        } catch (Exception e) {

        throw new RuntimeException(e);
    }
}
}
