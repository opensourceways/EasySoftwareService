package com.easysoftware.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    /**
     * Autowired KafkaTemplate for producing messages.
     */
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Static KafkaProducer for handling Kafka operations.
     */
    private static KafkaProducer<String, String> producer;

    /**
     * Sends a message with the specified topic, key, and value.
     *
     * @param topic The Kafka topic to send the message to.
     * @param key The key associated with the message.
     * @param value The value of the message.
     */
    public void sendMess(final String topic, final String key, final String value) {
        ProducerRecord<String, String> mess = new ProducerRecord<String, String>(topic, key, value);
        kafkaTemplate.send(mess);
    }

}
