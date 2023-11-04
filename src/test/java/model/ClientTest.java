package model;

import org.ibd.model.clients.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    @Test
    void ClientAttrsTest() {
        Client client = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
        assertNotNull(client);
        assertEquals(client.getClientId(), 1L);
        assertEquals(client.getName(), "Name");
        assertEquals(client.getSurname(), "Surname");
        assertEquals(client.getAddress(), "Address");
        assertEquals(client.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client.getBalance(), BigDecimal.ZERO);

        client.setName("Name2");
        client.setSurname("Surname2");
        client.setAddress("Address2");
        client.setBirth(LocalDate.of(2222, 2, 2));
        client.setBalance(new BigDecimal(400));

        assertEquals(client.getName(), "Name2");
        assertEquals(client.getSurname(), "Surname2");
        assertEquals(client.getAddress(), "Address2");
        assertEquals(client.getBirth(), LocalDate.of(2222, 2, 2));
        assertEquals(client.getBalance(), new BigDecimal(400));
    }

    @Test
    void ClientEmptyTest() {
        Client client = new Client();
        assertNotNull(client);
        assertNull(client.getClientId());
        assertNull(client.getName());
        assertNull(client.getSurname());
        assertNull(client.getAddress());
        assertNull(client.getBirth());
        assertNull(client.getBalance());
    }

    @Test
    void ClientToStringTest() {
        Client client = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), BigDecimal.ZERO);
        assertEquals(client.toString(), "Client{clientId=1, name='Name', surname='Surname', address='Address', birth=2000-01-01, balance=0}");
    }
}
