package io.yaooo.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import io.yaooo.bolt.TESTBolt1;
import io.yaooo.bolt.TESTBolt2;
import io.yaooo.spout.TESTKafkaSpout;

import java.util.HashMap;
import java.util.Map;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class TESTTopology {

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new TESTKafkaSpout(""), 1);
        builder.setBolt("bolt1", new TESTBolt1(), 2).shuffleGrouping("spout");
        builder.setBolt("bolt2", new TESTBolt2(), 2).fieldsGrouping("bolt1", new Fields("word"));

        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_WORKERS, 1);
        conf.put(Config.TOPOLOGY_DEBUG, true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("kafka-storm", conf, builder.createTopology());

    }

}
