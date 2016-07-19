package io.yaooo.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class TESTBolt1 extends BaseBasicBolt {

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {

            String msg1 = input.getString(0);
            int id1 = input.getInteger(1);
            String time1 = input.getString(2);

            if (msg1 == null) {
                System.out.println("msg1消息为空");
            }

            System.out.println("Bolt1收到的来自Spout的消息是：" + msg1 + "|||" + id1 + "|||" + time1);

//            msg1 = "===bolt1===" + msg1;

            collector.emit(new Values(msg1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}



