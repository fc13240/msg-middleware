package io.yaooo.logging.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:日志json解析+过滤器类
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class LogFilterBolt extends BaseBasicBolt {

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String msg = input.getString(0);

            //过滤debug级别的消息
            if (msg.substring(1, 6) == "DEBUG") {
                msg = "";
                return;
            }

            collector.emit(new Values(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("filtered"));
    }
}



