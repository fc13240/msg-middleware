package io.yaooo.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerTest extends Thread {

    private String topic;

    public KafkaProducerTest(String topic) {
        super();
        this.topic = topic;
    }

    public static void main(String[] args) {
        new KafkaProducerTest("topic0715").start();// 使用kafka集群中创建好的主题 test
    }

    @Override
    public void run() {
        Producer producer = createProducer();
        for (int i = 0; i < 100; i++) {
            ProducerRecord record = new ProducerRecord<String, String>(topic, String.format("{\"type\":\"test-msg\", \"msg-time\":%d, \"msg-no\":%d}", System.currentTimeMillis(), i));
            producer.send(record);
            producer.flush();
            System.out.println("生产的消息是：" + record.toString());

            try {
                Thread.currentThread().sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Producer createProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "c01:9092,c02:9092,c03:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer(properties);
    }
}