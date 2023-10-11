package org.ibd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.exceptions.RepositoryException;
import org.ibd.factory.PurchaseFactory;
import org.ibd.manager.PurchaseManager;
import org.ibd.model.clients.Client;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.purchases.Purchase;
import org.ibd.model.weapons.HandGrenade;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Rifle;
import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws RepositoryException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(em);
        PurchaseRepository purchaseRepository = new PurchaseRepository(em);
        Client client = new Client(1L, "Jan", "Kowalski", "Poland", LocalDate.now());
        Pistol weapon = new Pistol(1L,"tester", "bron", 200F,"200f");
        HandGrenade weapon2 = new HandGrenade(1L,"tester", "bron", 200F,300, GrenadeType.Cluster);
        Rifle weapon3 = new Rifle(1L,"tester", "bron", 200F,"200f", 400F);
        clientRepository.addClient(client);
        Client client2 = clientRepository.getClient(1L);
        client2.setName("Andrzej");
        clientRepository.addClient(client2);

        clientRepository.modifyClientName(1L, "Jan");
        clientRepository.removeClient(client2);
        Client client3 = new Client(2L, "Jan", "Kowalski", "England", LocalDate.now());

        clientRepository.addClient(client3);
        Purchase purchase = PurchaseFactory.createPurchase(2L, client3, weapon);
        purchaseRepository.persistPurchase(purchase);
        Purchase purchase2 = purchaseRepository.getPurchase(2L);

        String weapon4 = String.valueOf(purchase2.getWeapon().getClass());
        System.out.printf(weapon4);
        Pistol pistol = (Pistol) purchase2.getWeapon();
        pistol.setName("PISTOLET");
        purchaseRepository.persistPurchase(purchase2);
        Purchase purchase3 = PurchaseFactory.createPurchase(3L, client3, weapon2);
        purchaseRepository.persistPurchase(purchase3);
        Client client4 = clientRepository.getClient(2L);
        System.out.printf("JAVA SIZE"+String.valueOf(client4.getPurchaseSet().size()));
        System.out.printf("DATABASE SIZE"+String.valueOf(purchaseRepository.getAllPurchasesForSingleClient(2L).size()));
        PurchaseManager purchaseManager = new PurchaseManager(purchaseRepository);
        System.out.printf(String.valueOf(purchaseManager.calculateClientExpenses(client4)));
    }
}