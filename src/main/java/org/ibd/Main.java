package org.ibd;

import org.ibd.controller.GunShop;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws RepositoryException {
        System.out.println("McDuck gun&fun shop");
        GunShop gunShop = new GunShop();
        Client client = new Client(0L, "Joe", "Ligma", "Uć", LocalDate.of(2000, 10, 1));
        //gunShop.clientRepository.addClient(client);
    }
}