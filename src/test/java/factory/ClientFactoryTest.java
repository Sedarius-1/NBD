package factory;

import org.ibd.factory.ClientFactory;
import org.ibd.model.clients.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientFactoryTest {

    @Test
    void ClientFactoryClientCreationTest() {
        Client client = ClientFactory.createClient(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
        assertNotNull(client);
        assertEquals(client.getClientId(), 1L);
        assertEquals(client.getName(), "Name");
        assertEquals(client.getSurname(), "Surname");
        assertEquals(client.getAddress(), "Address");
        assertEquals(client.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client.getBalance(), BigDecimal.ZERO);
    }
}
