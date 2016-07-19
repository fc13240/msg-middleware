//package io.yaooo;
//
//import org.apache.storm.Config;
//import org.apache.storm.LocalCluster;
//import org.apache.storm.StormSubmitter;
//import org.apache.storm.generated.AlreadyAliveException;
//import org.apache.storm.generated.AuthorizationException;
//import org.apache.storm.generated.InvalidTopologyException;
//import org.apache.storm.spout.SpoutOutputCollector;
//import org.apache.storm.task.OutputCollector;
//import org.apache.storm.task.TopologyContext;
//import org.apache.storm.topology.IRichBolt;
//import org.apache.storm.topology.IRichSpout;
//import org.apache.storm.topology.OutputFieldsDeclarer;
//import org.apache.storm.topology.TopologyBuilder;
//import org.apache.storm.tuple.Fields;
//import org.apache.storm.tuple.Tuple;
//import org.apache.storm.tuple.Values;
//import org.apache.storm.utils.Utils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//
//public class WordCountNew {
//
//
//    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
//
//        TopologyBuilder builder = new TopologyBuilder();
//
//        builder.setSpout("input", new WordProduct(), 1);
//        builder.setBolt("bolt_sentence", new SplitSentence(), 1).shuffleGrouping("input");
//        builder.setBolt("bolt_wordcounter", new WordCounter(), 1).fieldsGrouping("bolt_sentence", new Fields("word"));
//
//        Config config = new Config();
//
//        if (args != null && args.length > 0) {
//            config.setNumWorkers(3);
//            try {
//                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
//            } catch (AuthorizationException e) {
//                e.printStackTrace();
//            }
//        } else {
//            LocalCluster cluster = new LocalCluster();
//            System.out.println("start word count");
//            cluster.submitTopology("word count", config, builder.createTopology());
//        }
//
//
//    }
//
//    ///接入数据源spout
//    public static class WordProduct implements IRichSpout {
//        private static final long serialVersionUID = 1L;
//        Random rand;
//        private SpoutOutputCollector collector;
//
//        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
//            this.collector = collector;
//            rand = new Random();
//        }
//
//        public void close() {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void activate() {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void deactivate() {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void nextTuple() {
//            Utils.sleep(5000);
//            String[] sentences = new String[]{
//                    "this is a test sentence array",
//                    "01-hello world",
//                    "02-How are you",
//                    "03-I am fine",
//                    "04-Thank you",
//                    "05-The last sentence should be as fine as the sentence written by you"
//            };
////            String s = sentences[rand.nextInt(sentences.length)];
//
//            for (int i=0;i<sentences.length;i++){
//                collector.emit(new Values(sentences[i]));
//
//            }
//
////            collector.emit(new Values(s));
//        }
//
//        public void ack(Object msgId) {
//
//        }
//
//        public void fail(Object msgId) {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void declareOutputFields(OutputFieldsDeclarer declarer) {
//            declarer.declare(new Fields("sentence"));
//        }
//
//        public Map<String, Object> getComponentConfiguration() {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//    }
//
//    ///分词bolt
//    public static class SplitSentence implements IRichBolt {
//        private OutputCollector collector;
//
//        public void cleanup() {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void execute(Tuple input) {
//            String s = input.getString(0);
//            String[] words = s.split(" ");
//            for (String w : words) {
//                w = w.trim();
//                if (!w.isEmpty()) {
//                    collector.emit(new Values(w));
//                }
//            }
//            collector.ack(input);
//
//        }
//
//        public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
//            this.collector = arg2;
//        }
//
//        public void declareOutputFields(OutputFieldsDeclarer declarer) {
//            declarer.declare(new Fields("word"));
//
//        }
//
//        public Map<String, Object> getComponentConfiguration() {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//    }
//
//    ///词频统计bolt
//    public static class WordCounter implements IRichBolt {
//        Map<String, Integer> counts = new HashMap<String, Integer>();
//        private OutputCollector collector;
//
//        public void cleanup() {
//            // TODO Auto-generated method stub
//
//        }
//
//        public void execute(Tuple input) {
//            String w = input.getString(0);
//            Integer count = counts.get(w);
//            if (count == null) {
//                count = 1;
//            } else {
//                count++;
//            }
//            counts.put(w, count);
//            collector.emit(new Values("word", count));
//
//            //打印到控制台
//            System.out.println(w + ":" + Integer.toString(count));
//            collector.ack(input);
//        }
//
//        public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
//            this.collector = arg2;
//        }
//
//        public void declareOutputFields(OutputFieldsDeclarer declarer) {
//            declarer.declare(new Fields("word", "count"));
//        }
//
//        public Map<String, Object> getComponentConfiguration() {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//    }
//
//}