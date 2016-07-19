package io.yaooo.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import io.yaooo.common.HBaseOperations;

import java.util.concurrent.atomic.AtomicInteger;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/

public class TESTBolt2 extends BaseBasicBolt {

    private AtomicInteger counter = new AtomicInteger(0);

    public void execute(Tuple tuple, BasicOutputCollector collector) {

        String msg2 = tuple.getString(0);

        if (msg2 == null) {
            System.out.println("msg1消息为空");
        }

        System.out.println("Bolt2收到的来自Bolt1的消息是：" + msg2);


        HBaseOperations hBaseOperations = new HBaseOperations();


        String[] k = {"desc"};
        String[] v = {msg2};

        hBaseOperations.insertRow("table0715", "rowkey005", "usr", k, v);


//        msg2 = "===bolt2===" + msg2;
//        collector.emit(new Values(msg2, 1));

        System.out.println("Bolt2处理后的消息是：" + msg2);

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("word", "count"));

    }
}



