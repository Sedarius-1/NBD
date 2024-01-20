package org.ibd.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.ibd.exceptions.RepositoryException;
import org.ibd.model.clients.Client;

import java.util.Collections;
import java.util.Properties;

@Slf4j
public class KafkaCustomConsumer {

    private KafkaConsumer<String, String> kafkaConsumer;
    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaCustomConsumer(String topic) {

        this.kafkaConsumer = this.startConsumer(topic);
    }

    private KafkaConsumer<String, String> startConsumer(String topic) {

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,
                KafkaConfig.CONSUMER_GROUP);
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192,kafka2:9292,kafka3:9392");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                false);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<>(consumerProperties);

        consumer.subscribe(Collections.singleton(topic));
        return consumer;
    }

    public void readMessage() {
        ConsumerRecords<String, String> recordsCollection = this.kafkaConsumer.poll(0);


        recordsCollection.forEach(record ->
        {
            try {
                Client newClient = this.objectMapper.readValue(record.value(), Client.class);
                System.out.println("Record's partition: "+record.partition());
                KafkaController.repository.add(newClient);
                System.out.println(record.offset() + " " + KafkaController.repository.get(newClient.getClientId()));
                KafkaController.repository.remove(newClient.getClientId());
            } catch (JsonProcessingException | RepositoryException e) {
                throw new RuntimeException(e);
            }
            this.kafkaConsumer.commitAsync();
        });
    }


    public void closeConsumer() {
      this.kafkaConsumer.close();
    }

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            KafkaCustomConsumerThread kafkaCustomConsumerThread = new KafkaCustomConsumerThread();
            Thread startedThread = new Thread(kafkaCustomConsumerThread);
            startedThread.start();
        }
    }

}
