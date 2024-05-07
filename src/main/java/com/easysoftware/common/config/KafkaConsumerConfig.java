package com.easysoftware.common.config;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    /**
     * Bootstrap servers for Kafka connection.
     */
    @Value("${bootstrap.servers}")
    private String bootstrapServers;

    /**
     * Consumer group ID for Kafka consumer.
     */
    @Value("${consumer.groupId}")
    private String groupId;

    /**
     * SASL JAAS configuration for authentication.
     */
    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String authConfig;

    /**
     * SASL mechanism for authentication.
     */
    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String mechanism;

    /**
     * Location of the SSL trust store.
     */
    @Value("${spring.kafka.properties.ssl.truststore.location}")
    private String trustStoreLocation;


    /**
     * Configures a ConsumerFactory for processing Kafka messages with String key and value types.
     *
     * @return The configured ConsumerFactory.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // add SASL_SSL config
        configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.name);
        configProps.put(SaslConfigs.SASL_MECHANISM, mechanism);
        configProps.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        configProps.put(SaslConfigs.SASL_JAAS_CONFIG, authConfig);
        configProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, trustStoreLocation);

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    /**
     * Configures a Kafka listener container factory for processing Kafka messages.
     *
     * @return The ConcurrentKafkaListenerContainerFactory for String key and value types.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(true);
        return factory;
    }
}
