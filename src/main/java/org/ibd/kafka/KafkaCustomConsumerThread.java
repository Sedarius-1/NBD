package org.ibd.kafka;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaCustomConsumerThread implements Runnable{

        @Override
        public void run(){
            KafkaCustomConsumer kafkaCustomConsumer = new KafkaCustomConsumer(KafkaController.topic);
            log.info("Thread started! Thread is: "+Thread.currentThread());
            while (2==2){
                kafkaCustomConsumer.readMessage();
            }
        }
}
