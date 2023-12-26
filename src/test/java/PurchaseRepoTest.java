import org.cassandra.Consts;
import org.cassandra.Enums;
import org.cassandra.model.*;
import org.cassandra.repo.GeneralRepo;
import org.cassandra.repo.SessionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        pistol = new Pistol(pistolid, "Glock", "pistol", 100L,"Pistol", 20.0F);
        rifle = new Rifle(rifleid, "AR", "rifle", 200L,"Rifle", 80.0F);
        purchasePistol = new Purchase(purchasepistolid,client.getClientid(),pistol.getSerialnumber());
        purchaseRifle = new Purchase(purchaserifleid,client.getClientid(),rifle.getSerialnumber());
    }

    @Test
    public void overallPurchaseTest() {
        try (SessionController sessionController = new SessionController()) {
            sessionController.getCurrentSession().execute("TRUNCATE "+ Consts.defaultKeyspace+"."+Consts.purchaseTable+";");
            GeneralRepo<Purchase> testPurchaseRepo = sessionController.createClassRepository(Enums.accessType.PURCHASE);

            assertNull(testPurchaseRepo.select(purchasepistolid, purchasePistol.getClass().getSimpleName()));
            assertNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

            testPurchaseRepo.insert(purchasePistol);
            testPurchaseRepo.insert(purchaseRifle);

            assertNotNull(testPurchaseRepo.select(purchasepistolid, purchasePistol.getClass().getSimpleName()));
            assertNotNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

            Purchase purchasePistolSelected;
            Purchase purchaseRifleSelected;

            purchasePistolSelected=testPurchaseRepo.select(purchasepistolid,purchasePistol.getClass().getSimpleName());
            purchaseRifleSelected=testPurchaseRepo.select(purchaserifleid,purchaseRifle.getClass().getSimpleName());
            assertNotNull(purchasePistolSelected);
            assertNotNull(purchaseRifleSelected);

            testPurchaseRepo.delete(purchasePistolSelected);
            assertNull(testPurchaseRepo.select(purchasepistolid,purchasePistol.getClass().getSimpleName()));
            assertNotNull(testPurchaseRepo.select(purchaserifleid, purchaseRifle.getClass().getSimpleName()));

            testPurchaseRepo.delete(purchaseRifleSelected);
            assertNull(testPurchaseRepo.select(purchaserifleid,purchaseRifle.getClass().getSimpleName()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
