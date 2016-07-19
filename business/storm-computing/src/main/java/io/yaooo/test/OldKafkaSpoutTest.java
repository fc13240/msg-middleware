package io.yaooo.test;

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

public class OldKafkaSpoutTest implements IRichSpout {

    private static final long serialVersionUID = 1L;
    private SpoutOutputCollector collector;
    private ConsumerConnector consumer;
    private String topic;

    public OldKafkaSpoutTest() {
    }

    public OldKafkaSpoutTest(String topic) {
        this.topic = topic;
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

        System.out.println("Storm消费的Topic是：" + topic);

        Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topickMap);

        KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);

        ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
        while (iterator.hasNext()) {
            String value = new String(iterator.next().message());
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy年MM月dd日 HH:mm:ss SSS");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            String str = formatter.format(curDate);

            System.out.println("Storm收到的来自kafka的消息是：" + value);

            collector.emit(new Values(value, 1, str), value);
        }
    }

    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        // 设置zookeeper的链接地址
        // props.put("zookeeper.connect","m1:2181,m2:2181,s1:2181,s2:2181");

        props.put("zookeeper.connect", "c01:2181,c02:2181,c03:2181");

        // 设置group id
        props.put("group.id", "storm-group");
        // kafka的group 消费记录是保存在zookeeper上的, 但这个信息在zookeeper上不是实时更新的, 需要有个间隔时间更新
        props.put("auto.commit.interval.ms", "1000");
        props.put("zookeeper.session.timeout.ms", "10000");
        return new ConsumerConfig(props);
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
        topic = "topic0715";
        return null;
    }
}