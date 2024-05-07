package com.easysoftware.kafka;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.ServiceMap;
import com.easysoftware.common.utils.ObjectMapperUtil;
import lombok.Generated;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseConsumer {

    /**
     * Autowired ServiceMap instance.
     */
    @Autowired
    private ServiceMap serviceMap;

    /**
     * Logger for BaseConsumer class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseConsumer.class);

    /**
     * List to hold KafkaConsumer instances for String keys and values.
     */
    private final ArrayList<KafkaConsumer<String, String>> kafkaConsumerList = new ArrayList<>();


    /**
     * Custom tasks method to perform Kafka to MySQL data transfer.
     */
    // @Scheduled(fixedRate = 5000)
    @Generated
    public void tasks() {
        kafkaToMysql();
    }

    /**
     * Method to transfer data from Kafka to MySQL by processing ConsumerRecords.
     */
    @Generated
    public void kafkaToMysql() {
        while (true) {
            for (KafkaConsumer<String, String> customer : kafkaConsumerList) {
                ConsumerRecords<String, String> poll = customer.poll(Duration.ofSeconds(5));
                dealDataToTableByBatch(poll);
                customer.commitAsync();
            }
        }
    }

    /**
     * Processes ConsumerRecords in batches and deals with the data to insert into a table.
     *
     * @param records The ConsumerRecords to process.
     */
    // The data of a topic can only be written to the same table
    public void dealDataToTableByBatch(final ConsumerRecords<String, String> records) {
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
                baseIService = serviceMap.getIService(table + "Service");
                appList.add(value);
                partition = record.partition();
                offset = record.offset();
            } catch (Exception e) {
                LOGGER.error(e.getMessage() + ":" + value, e);
            }
        }
        long endTime1 = System.nanoTime();
        long duration = (endTime1 - startTime) / 1000000;
        LOGGER.info("处理records用时： " + duration + " 毫秒，" + "数据量：" + appList.size());
        if (!appList.isEmpty()) {
            LOGGER.info("partation: " + partition + ", offset: " + offset);
            baseIService.saveDataObjectBatch(appList);
        }
        long endTime2 = System.nanoTime();
        duration = (endTime2 - endTime1) / 1000000;
        LOGGER.info("写入数据库用时： " + duration + " 毫秒，" + "数据量：" + appList.size());
    }

    /**
     * Processes ConsumerRecords and deals with the data to insert into multiple tables.
     *
     * @param records The ConsumerRecords to process.
     */
    // The data of a topic may be written to multiple tables
    @Generated
    public void dealDataToMultipleTables(final ConsumerRecords<String, String> records) {
        Map<String, ArrayList<String>> resMap = new HashMap<>();
        int partition = 0;
        long offset = 0;

        for (ConsumerRecord<String, String> record : records) {
            String value = record.value();
            try {
                Map<String, Object> dtoMap = ObjectMapperUtil.toMap(value);
                String table = dtoMap.get("table").toString();

                if (!resMap.containsKey(table)) {
                    resMap.put(table, new ArrayList<>());
                }

                ArrayList<String> tmp = resMap.get(table);
                tmp.add(value);
                resMap.put(table, tmp);

                partition = record.partition();
                offset = record.offset();
            } catch (Exception e) {
                LOGGER.error(e.getMessage() + ": " + value, e);
            }
        }
        resMap.forEach((table, values) -> {
            if (!values.isEmpty()) {
                serviceMap.getIService(table + "Service").saveDataObjectBatch(values);
            }
        });
        LOGGER.info("Partition: " + partition + ", Offset: " + offset);
    }

}
