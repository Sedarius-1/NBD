package org.ibd.kafka;

import org.ibd.repository.ClientRepository;
import org.ibd.repository.WeaponRepository;

public class KafkaController {

    public static String topic = "clients";

    public static String consumersString = "consumers";

    public static ClientRepository repository = new ClientRepository();
}
