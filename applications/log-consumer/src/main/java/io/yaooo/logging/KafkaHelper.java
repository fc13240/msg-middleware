package io.yaooo.logging;

import java.util.Properties;

/******************************************************************************
 * Created by  Yao  on  2016/7/5
 * README:
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class KafkaHelper {

    public Properties assembleKafkaConfig(String strZKServers, String strGroupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", strZKServers);
        props.put("group.id", strGroupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public Properties assembleKafkaConfig(String strZKServers) {
        return this.assembleKafkaConfig(strZKServers, "all_groups");
    }

}
