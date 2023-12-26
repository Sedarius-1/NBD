import org.cassandra.Consts;
import org.cassandra.Enums;
import org.cassandra.model.Pistol;
import org.cassandra.model.Rifle;
import org.cassandra.model.Weapon;
import org.cassandra.repo.GeneralRepo;
import org.cassandra.repo.SessionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponRepoTest {
    private Pistol pistol;
    private Rifle rifle;
    private final Long pistolid = 1L;
    private final Long rifleid = 2L;

    String pistolNameChanged = "Pitsol";
    String rifleNameChanged = "Riefel";

    @BeforeEach
    public void setPurchase() {
        pistol = new Pistol(pistolid, "Glock", "pistol", 100L, "Pistol", 20.0F);
        rifle = new Rifle(rifleid, "AR", "rifle", 200L, "Rifle", 80.0F);
    }

    @Test
    public void overallWeaponTest() {
        try (SessionController sessionController = new SessionController()) {
            sessionController.getCurrentSession().execute("TRUNCATE " + Consts.defaultKeyspace + "." + Consts.weaponTable + ";");
            GeneralRepo<Weapon> testWeaponRepo = sessionController.createClassRepository(Enums.accessType.WEAPON);

            assertNull(testWeaponRepo.select(pistolid, pistol.getClass().getSimpleName()));
            assertNull(testWeaponRepo.select(rifleid, rifle.getClass().getSimpleName()));

            testWeaponRepo.insert(pistol);
            testWeaponRepo.insert(rifle);

            assertNotNull(testWeaponRepo.select(pistolid, pistol.getClass().getSimpleName()));
            assertNotNull(testWeaponRepo.select(rifleid, rifle.getClass().getSimpleName()));


            pistol.setName(pistolNameChanged);
            rifle.setName(rifleNameChanged);

            testWeaponRepo.update(pistol);
            testWeaponRepo.update(rifle);

            Pistol pistolSelected;
            Rifle rifleSelected;

            pistolSelected = (Pistol) testWeaponRepo.select(pistolid, pistol.getClass().getSimpleName());
            rifleSelected = (Rifle) testWeaponRepo.select(rifleid, rifle.getClass().getSimpleName());
            assertNotNull(pistolSelected);
            assertNotNull(rifleSelected);

            assertEquals(pistolNameChanged, pistolSelected.getName());
            assertEquals(rifleNameChanged, rifleSelected.getName());

            testWeaponRepo.delete(pistolSelected);
            assertNull(testWeaponRepo.select(pistolid, pistol.getClass().getSimpleName()));
            assertNotNull(testWeaponRepo.select(rifleid, rifle.getClass().getSimpleName()));

            testWeaponRepo.delete(rifleSelected);
            assertNull(testWeaponRepo.select(rifleid, rifle.getClass().getSimpleName()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
