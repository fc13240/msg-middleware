package io.yaooo.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.text.SimpleDateFormat;
import java.util.*;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:从Kafka读消息到Spout的类
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/

public class TESTKafkaSpout implements IRichSpout {

    private static final long serialVersionUID = 1L;
    private SpoutOutputCollector collector;
    private ConsumerConnector consumer;
    private String topic;

    public TESTKafkaSpout() {
    }

    public TESTKafkaSpout(String topic) {
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig() {

        ConsumerConfig result;
        Properties properties = new Properties();

        properties.put("zookeeper.connect", "c01:2181,c02:2181,c03:2181");
        // 设置group id
        properties.put("group.id", "storm-group");
        // kafka的group 消费记录是保存在zookeeper上的, 但这个信息在zookeeper上不是实时更新的, 需要有个间隔时间更新
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("zookeeper.session.timeout.ms", "10000");
        result = new ConsumerConfig(properties);
        return result;
    }

    public void nextTuple() {
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void ack(Object msgId) {
    }

    public void activate() {

        consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
        Map<String, Integer> topickMap = new HashMap<String, Integer>();
        topickMap.put(topic, 1);

        System.out.println("=====Storm消费的Topic是：" + topic);

        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topickMap);

        KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()) {
            String value = new String(iterator.next().message());
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss SSS");
            // 获取当前时间
            Date curDate = new Date(System.currentTimeMillis());

            String str = formatter.format(curDate);

            System.out.println("Storm收到的来自kafka的消息是：" + value);

            collector.emit(new Values(value, 1, str), value);
        }
    }

    public void close() {
    }

    public void deactivate() {
    }

    public void fail(Object msgId) {
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "id", "time"));
    }

    public Map<String, Object> getComponentConfiguration() {
        System.out.println("getComponentConfiguration被调用");
        topic = "topicall0712";
        return null;
    }
}