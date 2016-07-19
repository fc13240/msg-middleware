package io.yaooo.test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OldKafkaTopologyTest {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new OldKafkaSpoutTest(""), 1);
        builder.setBolt("bolt1", new Bolt1(), 2).shuffleGrouping("spout");
        builder.setBolt("bolt2", new Bolt2(), 2).fieldsGrouping("bolt1", new Fields("word"));

        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_WORKERS, 1);
        conf.put(Config.TOPOLOGY_DEBUG, true);
        // Config conf = new Config();
        // conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("kafka-storm", conf, builder.createTopology());

        // Utils.sleep(1000 * 60 * 5); // local cluster test ...
        // cluster.shutdown();
    }

    public static class Bolt1 extends BaseBasicBolt {

        public void execute(Tuple input, BasicOutputCollector collector) {
            try {

                String msg = input.getString(0);
                int id = input.getInteger(1);
                String time = input.getString(2);

                System.out.println("Bolt1收到的来自Spout的消息是：" + msg + "|||" + id + "|||" + time);


                msg = "===bolt1===" + msg;

                if (msg != null) {
                    collector.emit(new Values(msg));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }
    }

    public static class Bolt2 extends BaseBasicBolt {

        private AtomicInteger counter = new AtomicInteger(0);

        public void execute(Tuple tuple, BasicOutputCollector collector) {
            String msg = tuple.getString(0);
            System.out.println("Bolt2收到的来自Bolt1的消息是：" + msg);
            msg = "===bolt2===" + msg;
            collector.emit(new Values(msg, 1));
            System.out.println("Bolt2处理后的消息是：" + msg);


        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));

        }
    }
}