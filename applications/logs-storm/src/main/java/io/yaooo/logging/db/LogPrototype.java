package io.yaooo.logging.db;

/******************************************************************************
 * Created by  Yao  on  2016/7/20
 * README:日志Bean类，存入数据库的字段设计
 *        与LogBean相对应，名称不要随意更改
 * ============================================================================
 * CHANGELOG：
 ******************************************************************************/
public class LogPrototype {

    private int appCode;
    private String userId;
    private String userIp;

    private String logTags;
    private String logTime;
    private String logLevel;
    private String logLocation;
    private String msg;
    private String commentsField;

    public int getAppCode() {
        return appCode;
    }

    public void setAppCode(int appCode) {
        this.appCode = appCode;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogTags() {
        return logTags;
    }

    public void setLogTags(String logTags) {
        this.logTags = logTags;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = logLocation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCommentsField() {
        return commentsField;
    }

    public void setCommentsField(String commentsField) {
        this.commentsField = commentsField;
    }
}
