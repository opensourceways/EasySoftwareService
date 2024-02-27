package com.easysoftware.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.converter.ApplicationVersionConvertor;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Service
public class MyConsumer {
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

    @Autowired
    private ApplicationVersionService appVersionService;

    @Resource
    ApplicationVersionGateway AppVersionGateway;

    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static final Logger logger = LoggerFactory.getLogger(MyConsumer.class); 
    protected ArrayList<KafkaConsumer<String,String>>  KafkaConsumerList= new ArrayList<>();
    public static KafkaConsumer<String, String> consumer;

    @PostConstruct
    public void init() {
        initConsumer();
    }

    MyConsumer() {
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                KafkaToMysql();
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public void KafkaToMysql() {
        for (KafkaConsumer<String, String> customer : KafkaConsumerList) {
            ConsumerRecords<String, String> poll = customer.poll(2);
            dealDataByBatch(poll);
            customer.commitSync();
        }
    }

    public void dealData(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            try {
                ApplicationVersion appVersion = ObjectMapperUtil.jsonToObject(value, ApplicationVersion.class);
                boolean found = AppVersionGateway.existApp(appVersion.getName());
                if (found) {
                    logger.info(String.format("The software %s is existed", appVersion.getName()));
                    continue;
                }
                AppVersionGateway.save(appVersion);
                logger.info("partation: " + record.partition() + ", offset: " + record.offset());

            } catch (Exception e) {
                logger.error(e.getMessage() + ":" + value, e);
            }
        }
    }

    public void dealDataByBatch(ConsumerRecords<String, String> records) {
        Collection<ApplicationVersionDO> appList = new ArrayList<>();
        int partition = 0;
        long offset = 0;
        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            try {
                ApplicationVersion appVersion = ObjectMapperUtil.jsonToObject(value, ApplicationVersion.class);
                boolean found = AppVersionGateway.existApp(appVersion.getName());
                if (found) {
                    logger.info(String.format("The software %s is existed", appVersion.getName()));
                    continue;
                }
                ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForCreate(appVersion);
                appList.add(appVersionDO);
                partition = record.partition();
                offset = record.offset();

            } catch (Exception e) {
                logger.error(e.getMessage() + ":" + value, e);
            }
        }
        logger.info("partation: " + partition + ", offset: " + offset);
        appVersionService.saveBatch(appList);
    }

    public void initConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("enable.auto.commit", enableAutoCommit);
        props.put("auto.commit.interval.ms", autoCommitIntervalMs);
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        consumer = new KafkaConsumer<>(props);

        String[] topics = topicNames.split(",");
        String[] topciOffsets = topicOffset.split(",");
        for (String topciOffset : topciOffsets) {
            String[] tf = topciOffset.split(":");
            TopicPartition topicPartition = new TopicPartition(topics[0], Integer.parseInt(tf[0]));
            consumer.assign(Arrays.asList(topicPartition));
            consumer.seek(topicPartition, Integer.parseInt(tf[1]));
            KafkaConsumerList.add(consumer);
        }
    }
}
