package com.easysoftware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VersionConsumer extends BaseConsumer {

    /**
     * Listens for and processes ConsumerRecords of type <String, String>.
     *
     * @param records The ConsumerRecords to process.
     */
    @KafkaListener(topics = "software_test_version")
    public void listen(final ConsumerRecords<String, String> records) {
        dealDataToTableByBatch(records);
    }
}
