package io.yaooo.logging.topology;

import io.yaooo.logging.bolt.LogFilterBolt;
import io.yaooo.logging.bolt.LogStorageBolt;
import io.yaooo.logging.spout.LogSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import java.util.HashMap;
import java.util.Map;

/******************************************************************************
 * Created by  Yao  on  2016/7/20
 * README:日志处理Topology类
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class LogTopology {

    public static void main(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("log-spout", new LogSpout("topic-k"), 1);
        builder.setBolt("log-filter-bolt", new LogFilterBolt(), 2).shuffleGrouping("log-spout");
        builder.setBolt("log-storage-bolt", new LogStorageBolt(), 2).shuffleGrouping("log-filter-bolt");

        Map conf = new HashMap();
        conf.put(Config.TOPOLOGY_WORKERS, 1);
        conf.put(Config.TOPOLOGY_DEBUG, true);

        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("logs-storm", conf, builder.createTopology());
    }

}
