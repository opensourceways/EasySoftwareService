package com.easysoftware.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

// @Service
public class AppPkgConsumer extends BaseConsumer {
    @Value("${consumer.topic.name}")
    String topicName;

    @Value("${consumer.topic.offset}")
    String topicOffset;

    @PostConstruct
    private void init() {
        initConsumer(topicName + "_app", topicOffset);
    }
}
