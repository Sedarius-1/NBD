package org.ibd.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.ibd.model.clients.Client;
import org.ibd.model.purchases.Purchase;


import java.time.LocalDate;
import java.util.Properties;

import java.util.concurrent.Future;

@Slf4j
public class KafkaCustomProducer {

    private KafkaProducer<String,String> kafkaProducer;

    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaCustomProducer() {
        this.kafkaProducer = this.startProducer();
    }

    public KafkaProducer<String,String> startProducer(){
        Properties kafkaProducerProps = new Properties();
        kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192,kafka2:9292,kafka3:9392");
        kafkaProducerProps.put(ProducerConfig.CLIENT_ID_CONFIG,
                "local");
        kafkaProducerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        kafkaProducerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        kafkaProducerProps.put(ProducerConfig.ACKS_CONFIG, "all");
        kafkaProducerProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        return new KafkaProducer<>(kafkaProducerProps);
    }

    public void produceMessage(Client client){
        try {
            String clientString = this.objectMapper.writeValueAsString(client);
            ProducerRecord<String,String> producerRecord = new ProducerRecord<>(KafkaController.topic, client.getName()+" "+client.getSurname(), clientString);
            Future<RecordMetadata> sentMessage = this.kafkaProducer.send(producerRecord);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void produceMessage(Purchase purchase){
        try {
            String purchaseString = this.objectMapper.writeValueAsString(purchase);
            ProducerRecord<String,String> producerRecord = new ProducerRecord<>(KafkaController.topic, purchase.getClientId()+" "+purchase.getWeaponId(), purchaseString);
            Future<RecordMetadata> sentMessage = this.kafkaProducer.send(producerRecord);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void closeProducer() {
        this.kafkaProducer.close();
    }

    public static void main(String[] args) {
            KafkaCustomProducer kafkaCustomProducer = new KafkaCustomProducer();

//        for (int i = 0; i < 100; i++) {
//            Client client = null;
//            if(i % 3 == 0)
//            {
//                client = new Client((long) i, "Jan", "Zerownik", "Łódź", LocalDate.now(), 1F);
//            }
//            else if(i%3==1)
//            {
//                client = new Client((long) i, "Johann", "Jedenik", "Piotrków", LocalDate.now(), 2F);
//            }
//            else{
//                client = new Client((long) i, "Jean", "Dwójnik", "Stryków", LocalDate.now(), 3F);
//            }
//            System.out.println(client);
//            kafkaCustomProducer.produceMessage(client);
//        }
        for (int i = 0; i < 100; i++) {
            Purchase purchase;
            if(i % 3 == 0)
            {
                purchase = new Purchase(1L,2L,3L);
            }
            else if(i%3==1)
            {
                purchase = new Purchase(2L,1L,3L);
            }
            else{
                purchase = new Purchase(3L,2L,1L);
            }
            System.out.println(purchase);
            kafkaCustomProducer.produceMessage(purchase);
        }
    }
}
