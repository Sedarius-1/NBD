package org.ibd.kafka;

import org.ibd.repository.ClientRepository;
import org.ibd.repository.PurchaseRepository;
import org.ibd.repository.WeaponRepository;

public class KafkaController {

    public static String topic = "purchases";

    public static final String CONSUMER_GROUP =
            "consumers";

    //    public static ClientRepository repository = new ClientRepository();
    public static PurchaseRepository repository = new PurchaseRepository();
}

