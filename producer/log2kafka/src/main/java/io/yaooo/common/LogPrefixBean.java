package io.yaooo.common;

/******************************************************************************
 * Created by  Yao  on  2016/7/4
 * README:日志前缀拼装
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class LogPrefixBean {

    //appCode为0表示应用未知
    private int appCode = 0;
    private String userId = "";
    private String userIp = "";
    //LogType默认为一般，即无类别
    private LogType logType = LogType.GENERAL;

    public LogPrefixBean() {

    }

    public LogPrefixBean(String type) {
        logType = LogType.valueOf(type.toUpperCase());
    }

    public LogPrefixBean(int intAppcode, String strUserId, String strUserIp, String strType) {
        this.appCode = intAppcode;
        this.userId = strUserId;
        this.userIp = strUserIp;
        this.logType = LogType.valueOf(strType.toUpperCase());
    }


    public int getAppCode() {
        return appCode;
    }

    public void setAppCode(int appCode) {
        this.appCode = appCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }


    @Override
    public String toString() {
        String str = "{\"appCode\":" + appCode +
                ",\"userId\":" + userId +
                ",\"userIp\":" + userIp +
                ",\"logType\":" + logType +
                "}";
        return str;
    }
}
