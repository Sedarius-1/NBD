import org.cassandra.Consts;
import org.cassandra.Enums;
import org.cassandra.model.*;
import org.cassandra.repo.GeneralRepo;
import org.cassandra.repo.PurchaseRepo;
import org.cassandra.repo.SessionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseRepoTest {

    private Client client;
    private Pistol pistol;
    private Rifle rifle;
    private Purchase purchasePistol;
    private Purchase purchaseRifle;
    private final Long clientid = 1L;
    private final Long pistolid = 2L;
    private final Long rifleid = 3L;
    private final Long purchasepistolid = 4L;
    private final Long purchaserifleid = 5L;

    @BeforeEach
    public void setPurchase() {
        String name = "Jan";
        client = new Client(clientid, name, "Testowy", "Adres", LocalDate.now(), 22544L);
        pistol = new Pistol(pistolid, "Glock", "pistol", 100L, "Pistol", 20.0F);
        rifle = new Rifle(rifleid, "AR", "rifle", 200L, "Rifle", 80.0F);
        purchasePistol = new Purchase(purchasepistolid, client.getClientid(), pistol.getSerialnumber());
        purchaseRifle = new Purchase(purchaserifleid, client.getClientid(), rifle.getSerialnumber());
    }

    @Test
    public void overallPurchaseTest() {
        try (SessionController sessionController = new SessionController()) {
            PurchaseRepo testPurchaseRepo = (PurchaseRepo) sessionController.createClassRepository(Enums.accessType.PURCHASE);
            sessionController.getCurrentSession().execute("TRUNCATE " + Consts.defaultKeyspace + "." + Consts.purchaseTable + ";");
            sessionController.getCurrentSession().execute("TRUNCATE " + Consts.defaultKeyspace + "." + Consts.purchaseTableByClient + ";");
            sessionController.getCurrentSession().execute("TRUNCATE " + Consts.defaultKeyspace + "." + Consts.purchaseTableByWeapon + ";");

            assertNull(testPurchaseRepo.select(purchasepistolid, purchasePistol.getClass().getSimpleName()));
            assertNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

            testPurchaseRepo.insert(purchasePistol);
            testPurchaseRepo.insert(purchaseRifle);

            assertNotNull(testPurchaseRepo.select(purchasepistolid, purchasePistol.getClass().getSimpleName()));
            assertNotNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

            ArrayList<Purchase> clientPurchases = testPurchaseRepo.selectPurchasesByClientId(client.getClientid());
            assertEquals(2, clientPurchases.size());
            if (Objects.equals(clientPurchases.get(0).getPurchaseid(), purchasePistol.getPurchaseid())) {
                assertEquals(purchasePistol.getWeaponid(), clientPurchases.get(0).getWeaponid());
                assertEquals(purchaseRifle.getPurchaseid(), clientPurchases.get(1).getPurchaseid());
                assertEquals(purchaseRifle.getWeaponid(), clientPurchases.get(1).getWeaponid());
            } else if (Objects.equals(clientPurchases.get(0).getPurchaseid(), purchaseRifle.getPurchaseid())) {
                assertEquals(purchaseRifle.getWeaponid(), clientPurchases.get(0).getWeaponid());
                assertEquals(purchasePistol.getPurchaseid(), clientPurchases.get(1).getPurchaseid());
                assertEquals(purchasePistol.getWeaponid(), clientPurchases.get(1).getWeaponid());
            }

            ArrayList<Purchase> pistolPurchases = testPurchaseRepo.selectPurchasesByWeaponId(pistol.getSerialnumber());
            assertEquals(1, pistolPurchases.size());
            assertEquals(purchasePistol.getPurchaseid(), pistolPurchases.get(0).getPurchaseid());
            assertEquals(purchasePistol.getClientid(), pistolPurchases.get(0).getClientid());

            ArrayList<Purchase> riflePurchases = testPurchaseRepo.selectPurchasesByWeaponId(rifle.getSerialnumber());
            assertEquals(1, riflePurchases.size());
            assertEquals(purchaseRifle.getPurchaseid(), riflePurchases.get(0).getPurchaseid());
            assertEquals(purchaseRifle.getClientid(), riflePurchases.get(0).getClientid());

            Purchase purchasePistolSelected;
            Purchase purchaseRifleSelected;

            purchasePistolSelected = testPurchaseRepo.select(purchasepistolid, purchasePistol.getClass().getSimpleName());
            purchaseRifleSelected = testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName());
            assertNotNull(purchasePistolSelected);
            assertNotNull(purchaseRifleSelected);

            testPurchaseRepo.delete(purchasePistol);
            assertNull(testPurchaseRepo.select(purchasepistolid, purchasePistol.getClass().getSimpleName()));
            assertEquals(1, testPurchaseRepo.selectPurchasesByClientId(client.getClientid()).size());
            assertEquals(0, testPurchaseRepo.selectPurchasesByWeaponId(pistol.getSerialnumber()).size());
            assertEquals(1, testPurchaseRepo.selectPurchasesByWeaponId(rifle.getSerialnumber()).size());
            assertNotNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

            testPurchaseRepo.delete(purchaseRifle);
            assertEquals(0, testPurchaseRepo.selectPurchasesByClientId(client.getClientid()).size());
            assertEquals(0, testPurchaseRepo.selectPurchasesByClientId(rifle.getSerialnumber()).size());
            assertNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
