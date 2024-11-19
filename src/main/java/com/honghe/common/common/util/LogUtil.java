package com.honghe.common.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志生成、打印工具类
 */
public class LogUtil {
    static Log logger = LogFactory.getLog(LogUtil.class);
    private static ThreadLocal<Object> threadFlag = new ThreadLocal<Object>();

    /*
     * 取得日志实例
     * @return
     */
    private synchronized static Log getLogger() {
        if(logger == null) {
            logger = LogFactory.getLog(LogUtil.class);
        }
        return logger;
    }

    /**
     * 严重错误（1/6）
     * 非常严重的错误，导致系统中止。
     * @param logMsg
     */
    public static void fatal(LogMsg logMsg) {
        String logMessage = constructMsg(logMsg);
        getLogger().fatal(logMessage);
    }

    /**
     * 非预期的运行时错误（2/6）
     * 其它运行期错误或不是预期的条件。
     * @param logMsg
     */
    public static void error(LogMsg logMsg) {
        String logMessage = constructMsg(logMsg);
        getLogger().error(logMessage);
    }

    /**
     * 警告（3/6）
     * 使用了不赞成使用的API、非常拙劣使用API, '几乎就是'错误, 其它运行时不合需要和不合预期的状态但还没必要称为 "错误"。
     * @param logMsg
     */
    public static void warn(LogMsg logMsg) {
        String logMessage = constructMsg(logMsg);
        logMsg.setE(null);
        getLogger().warn(logMessage);
    }

    /**
     * 一般信息（4/6）
     * 运行时产生的有意义的事件。
     * @param logMsg
     */
    public static void info(LogMsg logMsg) {
        String logMessage = constructMsg(logMsg);
        logMsg.setE(null);
        getLogger().info(logMessage);
    }

    /**
     * 调试信息（5/6）
     * 系统流程中的细节信息，一般正常运行时不被打印。
     * @param logMsg
     */
    public static void debug(LogMsg logMsg) {
        String logMessage = constructMsg(logMsg);
        logMsg.setE(null);
        getLogger().debug(logMessage);
    }

    /**
     * 更细节的调试信息（6/6）
     * 比debug更加细节的信息，一般正常运行时不被打印。
     * @param logMsg
     */
    public static void trace(LogMsg logMsg) {
        String logMessage = constructMsg(logMsg);
        logMsg.setE(null);
        getLogger().trace(logMessage);
    }


    /*
     * 构造完整的日志信息
     */
    private static String constructMsg(LogMsg logMsg) {
        StringBuffer cMsg = new StringBuffer();
        constructMsgForLogMsg(cMsg,logMsg);
        return cMsg.toString();
    }

    /**
     * 构建真正要打印的日志信息
     * @param cMsg
     * @param logMsg
     */
    private  static void constructMsgForLogMsg(StringBuffer cMsg, LogMsg logMsg) {
        if (logMsg != null) {
            cMsg.append("[").append(logMsg.getPlatform() == null ? "" : logMsg.getPlatform()).append("] @@ ");
            cMsg.append("[").append(logMsg.getModel() == null ? "" : logMsg.getModel()).append("] @@ ");
            cMsg.append("[").append(logMsg.getOperation() == null ? "" : logMsg.getOperation()).append("] @@ ");
            cMsg.append("[").append(logMsg.getMsg() == null ? "" : logMsg.getMsg()).append("] @@ ");
            if (logMsg.getE() != null) {
                Exception e = logMsg.getE();
                cMsg.append("\n[\n");
//                if (e.getStackTrace() != null && e.getStackTrace().length >= 0) {
//                    cMsg.append("\t[class：").append(e.getStackTrace()[0].getClassName()).append(";");
//                    cMsg.append("function：").append(e.getStackTrace()[0].getMethodName()).append(";");
//                    cMsg.append("line：").append(e.getStackTrace()[0].getLineNumber()).append("]\n");
//                    cMsg.append("\tdetails：[").append("\n");
//                    for (int i = 0; i < e.getStackTrace().length; i++) {
//                        cMsg.append("\t\t").append(e.getStackTrace()[i].toString()).append(";\n");
//                    }
//                    cMsg.append("\t]\n");
//                }
                cMsg.append(e.toString());
                cMsg.append("]");
            } else {
                cMsg.append("[]");
            }
        }
    }

    public static void main(String[] args) {
        String s = null;
        try {

            Class locationInfoCls = Class.forName("org.apache.log4j.spi.LocationInf");
            locationInfoCls.getResource("ss");
            if ("123".equals(s)) {

            }
        }catch (Exception e){
            logger.error(e);
            LogUtil.error(new LogMsg("测试","测试模块","测试日志的生成","",e));
        }
    }

    /**
     * 日志对象
     */
    public static class LogMsg{
        private String platform;
        private String model;
        private String operation;
        private String createTime;
        private String msg;
        private Exception e;

        private LogMsg(){}

        public LogMsg(String platform){
            this.platform = platform;
        }

        public LogMsg(String platform, String model){
            this.platform = platform;
            this.model = model;
        }

        public LogMsg(String platform,String model,String operation,String msg){
            this.platform = platform;
            this.model = model;
            this.createTime = DateUtil.currentDatetime();
            this.operation = operation;
            this.msg = msg;
        }

        public LogMsg(String platform,String model,String operation,String msg,Exception e){
            this.platform = platform;
            this.model = model;
            this.createTime = DateUtil.currentDatetime();
            this.operation = operation;
            this.msg = msg;
            this.e = e;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Exception getE() {
            return e;
        }

        public void setE(Exception e) {
            this.e = e;
        }
    }
}
