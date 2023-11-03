package repository;

import com.mongodb.client.model.Updates;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;
import org.ibd.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    @Test
    void ClientRepositoryCreateClientTest() throws RepositoryException {

        ClientRepository clientRepository = new ClientRepository();
        Client client1 = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
        clientRepository.add(client1); // TODO: add doesNotThrow assert

        Client client2 = clientRepository.get(1L);
        assertNotNull(client2);
        assertEquals(client2.getClientId(), 1L);
        assertEquals(client2.getName(), "Name");
        assertEquals(client2.getSurname(), "Surname");
        assertEquals(client2.getAddress(), "Address");
        assertEquals(client2.getBirth(), LocalDate.of(2000, 1, 1));
        assertEquals(client2.getBalance(), BigDecimal.ZERO);

        clientRepository.remove(1L);

        assertThrows(RepositoryException.class, () -> clientRepository.get(1L));
    }

    @Test
    void ClientRepositoryClientModifyTest() throws RepositoryException {
        ClientRepository clientRepository = new ClientRepository();
        Client client1 = new Client(1L, "Name", "Surname",
                "Address", LocalDate.of(2000, 1, 1), new BigDecimal(0));
        clientRepository.add(client1);

        clientRepository.updateOne(1L, Updates.set("name","Name2"));
        clientRepository.updateOne(1L, Updates.set("surname","Surname2"));
        clientRepository.updateOne(1L, Updates.set("address","Address2"));
        clientRepository.updateOne(1L, Updates.set("birth",LocalDate.of(2222, 11, 22)));
        clientRepository.updateOne(1L, Updates.set("balance",new BigDecimal(2137)));

        Client client2 = clientRepository.get(1L);
        assertNotNull(client2);
        assertEquals(client2.getClientId(), 1L);
        assertEquals("Name2", client2.getName());
        assertEquals("Surname2", client2.getSurname());
        assertEquals("Address2", client2.getAddress(), "Address2");
        assertEquals(LocalDate.of(2222, 11, 22), client2.getBirth());
        assertEquals(new BigDecimal(2137), client2.getBalance());
    }

    @Test
    void ClientRepositoryCreateClientFailureTest() {

        ClientRepository clientRepository = new ClientRepository();
        assertThrows(RepositoryException.class, () -> clientRepository.add(null));
    }

//    @Test
//    void ClientRepositoryRemoveFailureTest() {
//
//        ClientRepository clientRepository = new ClientRepository();
//        assertThrows(RepositoryException.class, () -> clientRepository.remove(5L));
//    }

//    @Test
//    void ClientRepositoryModifyFailureTest() {
//
//        ClientRepository clientRepository = new ClientRepository();
//        assertThrows(RepositoryException.class, () ->  clientRepository.updateOne(125L, Updates.set("name","Name2")));
//        assertThrows(RepositoryException.class, () ->  clientRepository.updateOne(125L, Updates.set("surname","Surname2")));
//        assertThrows(RepositoryException.class, () ->  clientRepository.updateOne(125L, Updates.set("address","Address2")));
//        assertThrows(RepositoryException.class, () ->  clientRepository.updateOne(125L, Updates.set("birth",LocalDate.of(2222, 11, 22))));
//        assertThrows(RepositoryException.class, () ->  clientRepository.updateOne(125L, Updates.set("balance",new BigDecimal(2137))));
//
//    }
}
