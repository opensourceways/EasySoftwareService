package com.easysoftware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AppPkgConsumer extends BaseConsumer {

    @KafkaListener(topics = "software_test_app")
    public void listen(ConsumerRecords<String, String> records) {
        dealDataToTableByBatch(records);
    }
}