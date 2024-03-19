package com.easysoftware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class VersionConsumer extends BaseConsumer {

    @KafkaListener(topics = "software_test_version")
    public void listen(ConsumerRecords<String, String> records) {
        dealDataToTableByBatch(records);
    }
}
