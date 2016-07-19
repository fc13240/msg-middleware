//package io.yaooo;
//
//import backtype.storm.Config;
//import backtype.storm.LocalCluster;
//import backtype.storm.StormSubmitter;
//import backtype.storm.generated.AlreadyAliveException;
//import backtype.storm.generated.InvalidTopologyException;
//import backtype.storm.spout.SpoutOutputCollector;
//import backtype.storm.task.OutputCollector;
//import backtype.storm.task.TopologyContext;
//import backtype.storm.topology.IRichBolt;
//import backtype.storm.topology.IRichSpout;
//import backtype.storm.topology.OutputFieldsDeclarer;
//import backtype.storm.topology.TopologyBuilder;
//import backtype.storm.tuple.Fields;
//import backtype.storm.tuple.Tuple;
//import backtype.storm.tuple.Values;
//import backtype.storm.utils.Utils;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//
//public class WordCountOld {
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
//            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
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