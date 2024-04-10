package com.easysoftware.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static KafkaProducer<String, String> producer;

    public void sendMess(String topic, String key, String value) {
        ProducerRecord<String, String> mess = new ProducerRecord<String, String>(topic, key, value);
        kafkaTemplate.send(mess);
    }
}
