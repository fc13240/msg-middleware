package io.yaooo;

import io.yaooo.common.LogPrefixBean;
import io.yaooo.utils.JsonHelper;

import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/******************************************************************************
 * Created by  Yao  on  2016/7/4
 * README:日志打印工具类
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class Log2Kafka {

    //实际日志打印对象
    public Logger logger = null;
    //javabean转json对象
    public JsonHelper jsonHelper = null;
    //日志前缀
    public LogPrefixBean logPrefixBean = null;


    Log2Kafka(Class c, String type) {
        logPrefixBean = new LogPrefixBean(type);
        logger = LoggerFactory.getLogger(c);
    }


    Log2Kafka(Class c2) {
        this(c2, "general");
    }


    public void info(String strP) {
        strP = logPrefixBean.toString() + strP;
        logger.info(strP);
    }

    public void info(Object o) {
        String s = logPrefixBean.toString() + jsonHelper.javaBean2JsonString(o);
        logger.info(s);
    }

    public void warn(String strP) {
        strP = logPrefixBean.toString() + strP;
        logger.warn(strP);
    }

    public void warn(Object o) {
        String s = logPrefixBean.toString() + jsonHelper.javaBean2JsonString(o);
        logger.warn(s);
    }

    public void error(String strP,Exception e1) {
        strP = logPrefixBean.toString() + strP;
        logger.error(strP,e1);
    }

//    public void error(Object o) {
//        String s = logPrefixBean.toString() + jsonHelper.javaBean2JsonString(o);
//        logger.error(s);
//    }


}
