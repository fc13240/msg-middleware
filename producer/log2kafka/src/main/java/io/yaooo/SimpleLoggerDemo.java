package io.yaooo;


public class SimpleLoggerDemo {

//    public static final Logger logger= LoggerFactory.getLogger(SimpleLoggerDemo.class);

    Log2Kafka logger = new Log2Kafka(SimpleLoggerDemo.class);

    public void logTest() {


        for (int i = 100; i < 150; i++) {
            if (i % 2 == 0) {
                logger.info("WARN - [" + i + "]");

            } else {
                logger.warn("INFO - [" + i + "]");
            }

            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

//        try {
//            int[] a = {1, 2, 3};
//            a[3] = a[1] + a[2];
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("",e);
//        }


    }
}