package io.yaooo.logging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/******************************************************************************
 * Created by  Yao  on  2016/7/5
 * README:
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class LogReading {

    public void printLog(String topic) {
        String zkServers = "192.168.100.121:9092,192.168.100.12:9092,192.168.100.123:9092";
        Properties p = new KafkaHelper().assembleKafkaConfig(zkServers);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(p);
        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);

            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
        }
    }
}
