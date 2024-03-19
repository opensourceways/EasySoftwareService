package com.easysoftware.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.ServiceMap;
import com.easysoftware.common.utils.ObjectMapperUtil;

// @Service
public class BaseConsumer {
    // @Autowired
    // KafkaConsumer<String, String> consumer;

    @Autowired
    ServiceMap serviceMap;

    private static final Logger logger = LoggerFactory.getLogger(BaseConsumer.class);
    protected ArrayList<KafkaConsumer<String, String>> KafkaConsumerList = new ArrayList<>();

    // @Scheduled(fixedRate = 5000)
    public void tasks() {
        KafkaToMysql();
    }


    // protected void initConsumer(String topicName, String topicOffset) {
    //     String[] topciOffsets = topicOffset.split(",");
    //     for (String topciOffset : topciOffsets) {
    //         String[] tf = topciOffset.split(":");
    //         TopicPartition topicPartition = new TopicPartition(topicName, Integer.parseInt(tf[0]));
    //         consumer.assign(Arrays.asList(topicPartition));
    //         consumer.seek(topicPartition, Integer.parseInt(tf[1]));
    //         KafkaConsumerList.add(consumer);
    //     }
    // }

    public void KafkaToMysql() {
        for (KafkaConsumer<String, String> customer : KafkaConsumerList) {
            ConsumerRecords<String, String> poll = customer.poll(Duration.ofSeconds(5));
            dealDataToTableByBatch(poll);
            customer.commitAsync();
        }
    }

    public void dealData(ConsumerRecords<String, String> records) {
        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            try {
                Map<String, Object> dtoMap = ObjectMapperUtil.toMap(value);
                String table = dtoMap.get("table").toString();
                String name = dtoMap.get("name").toString();
                BaseIService baseIService = serviceMap.getIService(table + "Service");
                if (baseIService.existApp(name)) {
                    logger.info(String.format("The software %s is existed", name));
                    continue;
                }
                baseIService.saveDataObject(value);
                logger.info("partation: " + record.partition() + ", offset: " + record.offset());

            } catch (Exception e) {
                logger.error(e.getMessage() + ":" + value, e);
            }
        }
    }

    // The data of a topic can only be written to the same table
    public void dealDataToTableByBatch(ConsumerRecords<String, String> records) {
        ArrayList<String> appList = new ArrayList<>();
        BaseIService baseIService = null;
        int partition = 0;
        long offset = 0;
        long startTime = System.nanoTime();
        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            try {
                Map<String, Object> dtoMap = ObjectMapperUtil.toMap(value);
                String table = dtoMap.get("table").toString();
                // String unique = dtoMap.get("unique").toString();
                baseIService = serviceMap.getIService(table + "Service");
                // if (baseIService.existApp(unique)) {
                //     logger.info(String.format("The software %s is existed", unique));
                //     continue;
                // }
                appList.add(value);
                partition = record.partition();
                offset = record.offset();
            } catch (Exception e) {
                logger.error(e.getMessage() + ":" + value, e);
            }
        }
        long endTime1 = System.nanoTime();
        long duration = (endTime1 - startTime) / 1000000;
        logger.info("处理records用时： " + duration + " 毫秒，" + "数据量：" + appList.size());
        if (!appList.isEmpty()) {
            logger.info("partation: " + partition + ", offset: " + offset);
            baseIService.saveDataObjectBatch(appList);
        }
        long endTime2 = System.nanoTime();
        duration = (endTime2 - endTime1) / 1000000;
        logger.info("写入数据库用时： " + duration + " 毫秒，" + "数据量：" + appList.size());
    }

    // The data of a topic may be written to multiple tables
    public void dealDataToMultipleTables(ConsumerRecords<String, String> records) {
        Map<String, ArrayList<String>> resMap = new HashMap<>();
        int partition = 0;
        long offset = 0;

        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            try {
                Map<String, Object> dtoMap = ObjectMapperUtil.toMap(value);
                String table = dtoMap.get("table").toString();
                String name = dtoMap.get("name").toString();

                BaseIService baseIService = serviceMap.getIService(table + "Service");
                if (baseIService.existApp(name)) {
                    logger.info(String.format("The software %s is already existed", name));
                    continue;
                }

                if (!resMap.containsKey(table)) {
                    resMap.put(table, new ArrayList<>());
                }

                ArrayList<String> tmp = resMap.get(table);
                tmp.add(value);
                resMap.put(table, tmp);

                partition = record.partition();
                offset = record.offset();
            } catch (Exception e) {
                logger.error(e.getMessage() + ": " + value, e);
            }
        }
        resMap.forEach((table, values) -> {
            if (!values.isEmpty()) {
                serviceMap.getIService(table + "Service").saveDataObjectBatch(values);
            }
        });
        logger.info("Partition: " + partition + ", Offset: " + offset);
    }

}
