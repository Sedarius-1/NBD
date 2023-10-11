package org.ibd;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.model.enums.GrenadeType;
import org.ibd.model.weapons.HandGrenade;
import org.ibd.model.weapons.Pistol;
import org.ibd.model.weapons.Rifle;
import org.ibd.repository.ClientRepository;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws RepositoryException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(em);
        Client client = new Client(1L, "Jan", "Kowalski", "Poland", LocalDate.now());
        Pistol weapon = new Pistol(1L,"tester", "bron", 200F,"200f");
        HandGrenade weapon2 = new HandGrenade(1L,"tester", "bron", 200F,300, GrenadeType.Cluster);
        Rifle weapon3 = new Rifle(1L,"tester", "bron", 200F,"200f", 400F);
        clientRepository.addClient(client);
        Client client2 = clientRepository.getClient(1L);
        client2.setName("Andrzej");
        clientRepository.addClient(client2);

        clientRepository.modifyClientName(1L, "Jan");
        em.getTransaction().begin();
        em.persist(client);
//        client.setName("TEST");
//        client.setBirth(LocalDate.of(2001, 10, 1));
//        em.persist(client);
        em.persist(weapon);
        em.persist(weapon2);
        em.persist(weapon3);
        em.getTransaction().commit();
    }
}