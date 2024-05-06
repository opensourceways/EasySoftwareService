package com.easysoftware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RpmConsumer extends BaseConsumer {
    /**
     * Value for the Kafka consumer topic name.
     */
    @Value("${consumer.topic.name}")
    private String topicName;

    /**
     * Value for the Kafka consumer topic offset.
     */
    @Value("${consumer.topic.offset}")
    private String topicOffset;

    /**
     * Kafka listener method that listens to the "software_test_rpm" topic with concurrency set to 3.
     *
     * @param records The ConsumerRecords to process.
     */
    @KafkaListener(topics = "software_test_rpm", concurrency = "3")
    public void listen(final ConsumerRecords<String, String> records) {
        dealDataToTableByBatch(records);
    }

}
