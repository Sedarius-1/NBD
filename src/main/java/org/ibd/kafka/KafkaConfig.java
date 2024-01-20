package org.ibd.kafka;

public class KafkaConfig {

    public enum Topic {
        CLIENTS,
        WEAPONS,
        PURCHASES
    }

    public static final String CONSUMER_GROUP =
            "consumers";
}

