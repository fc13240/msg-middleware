package io.yaooo.logging;

/**
 * 程序入口
 */
public class App {
    public static void main(String[] args) {
        System.out.println("===========Consumer BEGIN==========");
        LogReading logReading = new LogReading();
        logReading.printLog("topic-k");
    }
}
