import org.cassandra.Consts;
import org.cassandra.Enums;
import org.cassandra.model.Client;
import org.cassandra.repo.GeneralRepo;
import org.cassandra.repo.SessionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepoTest {

    private Client client;
    private final Long id = 1L;

    @BeforeEach
    public void setClient() {
        String name = "Jan";
        client = new Client(id, name, "Testowy", "Adres", LocalDate.now(), 22544L);

    }

    @Test
    public void overallClientTest() {
        try (SessionController sessionController = new SessionController()) {

            GeneralRepo<Client> testClientRepo = sessionController.createClassRepository(Enums.accessType.CLIENT);
            sessionController.getCurrentSession().execute("TRUNCATE "+ Consts.defaultKeyspace+"."+Consts.clientTable+";");

            assertNull(testClientRepo.select(id, client.getClass().getSimpleName()));

            testClientRepo.insert(client);

            assertNotNull(id, client.getClass().getSimpleName());

            String nameChanged = "Janek";
            client.setName(nameChanged);
            testClientRepo.update(client);

            Client clientSelected;

            clientSelected=testClientRepo.select(id,client.getClass().getSimpleName());
            assertNotNull(clientSelected);
            assertEquals(nameChanged,clientSelected.getName());

            testClientRepo.delete(clientSelected);

            assertNull(testClientRepo.select(id,client.getClass().getSimpleName()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
