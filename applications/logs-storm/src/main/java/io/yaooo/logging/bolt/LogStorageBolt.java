package io.yaooo.logging.bolt;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.yaooo.logging.common.HBaseOperations;
import io.yaooo.logging.db.LogPrototype;
import io.yaooo.logging.db.RowkeyFactory;
import io.yaooo.logging.logging.info.LogBean;
import io.yaooo.logging.utils.JsonHelper;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/******************************************************************************
 * Created by  Yao  on  2016/7/15
 * README:日志存储类
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class LogStorageBolt extends BaseBasicBolt {

    JsonHelper jsonHelper;
    ObjectMapper mapper;
    JsonFactory factory;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
        jsonHelper = new JsonHelper();
        mapper = new ObjectMapper();
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            String msg = input.getString(0);

            if (msg.trim() == "" || msg == null) {
                return;
            }

            //TO-DO:优化插入数据库的代码
            //解析最初LogBean的部分
            String logBeanString = msg.substring(99);
            LogBean logBean = new LogBean();
            mapper = new ObjectMapper();
            logBean = mapper.readValue(logBeanString, logBean.getClass());

            //解析日志其他部分
            String logLevel = msg.substring(1, 6);
            String logTime = msg.substring(7, 30);
            String logLocation = msg.substring(30, 97);

            //拼凑入库的LogPrototype
            LogPrototype logPrototype = new LogPrototype();
            logPrototype.setAppCode(logBean.getAppCode());
            logPrototype.setUserId(logBean.getUserId());
            logPrototype.setUserIp(logBean.getUserIp());
            logPrototype.setLogTags(logBean.getLogTags().name());
            logPrototype.setLogTime(logTime);
            logPrototype.setLogLevel(logLevel);
            logPrototype.setLogLocation(logLocation);
            logPrototype.setMsg(logBean.getMsg());
            logPrototype.setCommentsField(logBean.getCommentsField());

            String logPrototypeString = jsonHelper.javaBean2JsonString(logPrototype);

            factory = new JsonFactory();

            JsonParser parser = factory.createParser(logPrototypeString);


            List<String> k = new ArrayList<String>();
            List<String> v = new ArrayList<String>();

            while (parser.nextToken() != JsonToken.END_OBJECT) {

                String fieldName = parser.getCurrentName();

                String fieldValue = parser.getText();

                //过滤掉为空的字段
                if (fieldName == null || fieldName.trim() == "" || fieldValue.trim() == "" || fieldValue == null || fieldValue == fieldName || fieldName.contains("{") || fieldName.contains("}")) {

                    continue;

                } else {

                    k.add(fieldName);
                    v.add(fieldValue);

                }
            }

            if (k == null) {

                return;

            } else {

                String rowkey = RowkeyFactory.build(logPrototype.getLogTags(), logPrototype.getLogTime());

                HBaseOperations hBaseOperations = new HBaseOperations();
                hBaseOperations.insertRow("table0715", rowkey, "loginfo", k.toArray(new String[k.size()]), v.toArray(new String[v.size()]));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("filtered"));
    }
}



