package io.yaooo.logging.spout;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:从Kafka读消息的Spout类，只负责数据传输，不处理
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/

public class LogSpout implements IRichSpout {

    private static final long serialVersionUID = 1L;
    private SpoutOutputCollector collector;
    private ConsumerConnector consumer;
    private String topic;

    public LogSpout() {
    }

    public LogSpout(String topic) {
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig() {

        ConsumerConfig result;
        Properties properties = new Properties();

        properties.put("zookeeper.connect", "c01:2181,c02:2181,c03:2181");
        // 设置group id
        properties.put("group.id", "logs-storm");
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
            //转发消息，不做处理
            collector.emit(new Values(value));
        }
    }

    public void close() {
    }

    public void deactivate() {
    }

    public void fail(Object msgId) {
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        System.out.println("=====declareOutputFields方法");
        declarer.declare(new Fields("original"));

    }

    public Map<String, Object> getComponentConfiguration() {
//        System.out.println("=====getComponentConfiguration被调用");
//        topic = "topic-k";
        return null;
    }
}