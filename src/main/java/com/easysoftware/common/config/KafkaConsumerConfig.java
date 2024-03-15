package com.easysoftware.common.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConfig {
    @Value("${consumer.topic.name}")
    String topicNames;

    @Value("${consumer.topic.offset}")
    String topicOffset;

    @Value("${bootstrap.servers}")
    String bootstrapServers;

    @Value("${consumer.groupId}")
    String groupId;

    @Value("${consumer.autoCommitIntervalMs}")
    String autoCommitIntervalMs;

    @Value("${consumer.enableAutoCommit}")
    String enableAutoCommit;

    @Bean
    public KafkaConsumer<String, String> kafkaConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", enableAutoCommit);
        props.put("auto.commit.interval.ms", autoCommitIntervalMs);
        props.put("request.timeout.ms", "300000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        return consumer;
    }
}
