package org.ibd;


import org.ibd.controller.GunShop;
import org.ibd.enums.ClientParamEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


public class Main {

    public static void main(String[] args) {
        // Sorry for the disgusting logs, MongoDB logger is broken (config issues)
        // Output is highlighted in green / red

        Logger log = LoggerFactory.getLogger("NBD");
//        try (ClientRepository clientRepository = new ClientRepository()) {
//            Client client = new Client(1L, "Name", "Surname",
//                    "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
//            clientRepository.add(client);
//            Client client1 = new Client(2L, "Name", "Surname",
//                    "Address1", LocalDate.of(2000, 1, 1), BigDecimal.ONE);
//            clientRepository.add(client1);
//            Client client2 = new Client(3L, "Name", "Surname",
//                    "Address2", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
//            clientRepository.add(client2);
//            Client client3 = new Client(4L, "Name", "Spurname",
//                    "Address3", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
//            clientRepository.add(client3);
//            ArrayList<Client> testDBItemArrayList = clientRepository.getAll();
//            testDBItemArrayList.forEach(clientIterator ->  System.out.println(AnsiCodes.ANSI_GREEN + clientIterator + AnsiCodes.ANSI_RESET)) ;
//            System.out.println(AnsiCodes.ANSI_GREEN + clientRepository.get(2L) + AnsiCodes.ANSI_RESET);
//            if(clientRepository.updateOne(1L,
//                    Updates.set("name", "stanislaw"))){
//                System.out.println(AnsiCodes.ANSI_GREEN + clientRepository.get(1L) + AnsiCodes.ANSI_RESET);
//
//            }
//            ArrayList<Client> testDBItemArrayList2 = clientRepository.find(and(eq("surname", "Surname"),eq("balance", BigDecimal.ZERO)));
//            testDBItemArrayList2.forEach(clientIterator ->  System.out.println(AnsiCodes.ANSI_GREEN + clientIterator + AnsiCodes.ANSI_RESET)) ;
//            clientRepository.remove(3L);
//            testDBItemArrayList2 = clientRepository.find(and(eq("surname", "Surname"),eq("balance", BigDecimal.ZERO)));
//            testDBItemArrayList2.forEach(clientIterator ->  System.out.println(AnsiCodes.ANSI_GREEN + clientIterator + AnsiCodes.ANSI_RESET)) ;
//
//        } catch (Exception e) {
//            System.out.println(AnsiCodes.ANSI_RED + "oops:\n" + e + AnsiCodes.ANSI_RESET);
//        }
        // TODO: read-write concern find out
//        try (WeaponRepository weaponRepository = new WeaponRepository()) {
//
//            Pistol pistol = new Pistol(1L, "uwuGuns",":3", BigDecimal.ZERO, "42.69");
//            weaponRepository.add(pistol);
//
//            Rifle rifle = new Rifle(2L, "uwuGuns","Yamete", BigDecimal.ZERO, "42.69", 25F);
//            weaponRepository.add(rifle);
//
//            HandGrenade handGrenade = new HandGrenade(3L, "uwuGun","MeowBoom uwu", BigDecimal.TEN, 426900, GrenadeType.Frag);
//            weaponRepository.add(handGrenade);
//            RecreationalMcNuke nuke = new RecreationalMcNuke(4L, "uwuGuns","Daddy?", BigDecimal.ZERO, 426900);
//            weaponRepository.add(nuke);
//            ArrayList<Weapon> testDBItemArrayList = weaponRepository.getAll();
//            testDBItemArrayList.forEach(weaponIterator ->  System.out.println(AnsiCodes.ANSI_GREEN + weaponIterator + AnsiCodes.ANSI_RESET)) ;
//            System.out.println(AnsiCodes.ANSI_RED + "niganiganiganiganiganiga" + AnsiCodes.ANSI_RESET);
//            System.out.println(AnsiCodes.ANSI_GREEN + weaponRepository.get(2L) + AnsiCodes.ANSI_RESET);
//            System.out.println(AnsiCodes.ANSI_RED + "niganiganiganiganiganiga" + AnsiCodes.ANSI_RESET);
//            if(weaponRepository.updateOne(1L,
//                    Updates.set("manufacturer", "stanislaw"))){
//                System.out.println(AnsiCodes.ANSI_GREEN + weaponRepository.get(1L) + AnsiCodes.ANSI_RESET);
//
//            }
//            System.out.println(AnsiCodes.ANSI_RED + "niganiganiganiganiganiga" + AnsiCodes.ANSI_RESET);
//            ArrayList<Weapon> testDBItemArrayList2 = weaponRepository.find(and(eq("manufacturer", "uwuGuns"),eq("price", BigDecimal.ZERO)));
//            testDBItemArrayList2.forEach(weaponIterator ->  System.out.println(AnsiCodes.ANSI_GREEN +  weaponIterator + AnsiCodes.ANSI_RESET)) ;
//            System.out.println(AnsiCodes.ANSI_RED + "niganiganiganiganiganiga" + AnsiCodes.ANSI_RESET);
//
//            weaponRepository.remove(4L);
//            testDBItemArrayList2 = weaponRepository.find(and(eq("manufacturer", "uwuGuns"),eq("price", BigDecimal.ZERO)));
//            testDBItemArrayList2.forEach(weaponIterator ->  System.out.println(AnsiCodes.ANSI_GREEN + weaponIterator + AnsiCodes.ANSI_RESET)) ;
//            System.out.println(AnsiCodes.ANSI_RED + "niganiganiganiganiganiga" + AnsiCodes.ANSI_RESET);
//
//
//        } catch (Exception e) {
//            System.out.println(AnsiCodes.ANSI_RED + "oops:\n" + e + AnsiCodes.ANSI_RESET);
//        }

        GunShop gunShop = new GunShop();
        gunShop.registerClient(1L, "Name", "Surname","Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
        gunShop.modifyClient(ClientParamEnum.NAME, "Stanislaw", 1L);
        System.out.println(gunShop.getClientInfo(1L));
    }
}