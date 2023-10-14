import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    @Test
    void ClientAttrsTest() {
        Client client = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1));
        assertNotNull(client);
        assertEquals(client.getClientId(), 1L);
        assertEquals(client.getName(), "Name");
        assertEquals(client.getSurname(), "Surname");
        assertEquals(client.getAddress(), "Address");
        assertEquals(client.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client.getPurchaseSet().size(), 0);

        client.setName("Name2");
        client.setSurname("Surname2");
        client.setAddress("Address2");
        client.setBirth(LocalDate.of(2222, 2, 2));
        client.setPurchaseSet(null);

        assertEquals(client.getName(), "Name2");
        assertEquals(client.getSurname(), "Surname2");
        assertEquals(client.getAddress(), "Address2");
        assertEquals(client.getBirth(), LocalDate.of(2222, 2, 2));
        assertNull(client.getPurchaseSet());
    }
}
