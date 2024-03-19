package com.easysoftware.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RpmConsumer extends BaseConsumer {
    @Value("${consumer.topic.name}")
    String topicName;

    @Value("${consumer.topic.offset}")
    String topicOffset;

    @KafkaListener(topics = "software_test_rpm", concurrency = "3")
    public void listen(ConsumerRecords<String, String> records) {
        dealDataToTableByBatch(records);
    }
}
