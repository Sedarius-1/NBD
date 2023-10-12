package org.ibd;

import org.ibd.controller.GunShop;
import org.ibd.exceptions.RepositoryException;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws RepositoryException {
        GunShop gunShop = new GunShop();
        gunShop.registerClient(1L, "Tomasz", "Kowalczyk", "Bolzga", LocalDate.of(2003, 2, 5));
    }
}