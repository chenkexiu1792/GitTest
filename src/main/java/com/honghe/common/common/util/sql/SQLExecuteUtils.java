package com.honghe.common.common.util.sql;

import com.honghe.common.common.util.PathUtil;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 执行sql文件工具
 */
public class SQLExecuteUtils {
    /**
     * 连接信息
     */
    private static String url = "";
    private static String userName = "";
    private static String password = "";
    private static String defaultUrl = "";
    private static String defaultUserName = "";
    private static String defaultPassword = "";
    private static String[] params = new String[0];

    private SQLExecuteUtils() {
    }

    /**
     * 获取sql执行工具
     * 通过配置文件读取数据库url，用户名，密码信息
     *
     * @return
     */
    public static SQLExecuteUtils geInstance(String[] param) {
        loadProperties();
        params = param;
        return new SQLExecuteUtils();
    }

    private static void loadProperties() {
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(PathUtil.getPath(PathUtil.PathType.CONFIG) + "dataSourceConfig.properties"));
            //数据库连接地址
            url = pro.getProperty("spring.datasource.url");
            //数据库用户名
            userName = pro.getProperty("spring.datasource.username");
            //数据库密码
            password = pro.getProperty("spring.datasource.password");
            defaultUrl = pro.getProperty("defaulturl");
            //数据库用户名
            defaultUserName = pro.getProperty("defaulusername");
            //数据库密码
            defaultPassword = pro.getProperty("defaulpassword");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取sql执行工具
     * 通过配置文件读取数据库url，用户名，密码信息
     *
     * @return
     */
    public static SQLExecuteUtils geInstance() {
        loadProperties();
        return new SQLExecuteUtils();
    }

    /**
     * 执行sql
     *
     * @param sqlContent
     * @param dbName
     * @param sqlDelimiter
     */
    public boolean executeUserSQLFile(String sqlContent, String dbName, String sqlDelimiter) {
        SQLExecutor sqlExecutor;
        if (dbName != null && dbName.contains("_")) {
            defaultUrl = defaultUrl.replace("attendance", "");
            sqlContent = sqlContent.replace(" attendance_divide ", " "+dbName+" ");
            sqlExecutor = new SQLExecutor("com.mysql.jdbc.Driver", defaultUrl, defaultUserName, defaultPassword, sqlDelimiter);
        } else {
            url = url.replace(dbName, "");
            sqlExecutor = new SQLExecutor("com.mysql.jdbc.Driver", url, userName, password, sqlDelimiter);
        }
        try {
            sqlExecutor.importFile(sqlContent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 执行sql
     *
     * @param sqlContent
     * @param sqlDelimiter
     */
    public boolean executeSQL(String sqlContent, String sqlDelimiter) {
        SQLExecutor sqlExecutor = new SQLExecutor("com.mysql.jdbc.Driver", defaultUrl, defaultUserName, defaultPassword, sqlDelimiter);
        try {
            sqlExecutor.importFile(sqlContent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    class SQLExecutor {
        private String type;
        private String url;
        private String userName;
        private String password;
        private String delimiter;

        public SQLExecutor(String type, String url, String userName, String password, String delimiter) {
            this.type = type;
            this.url = url;
            this.userName = userName;
            this.password = password;
            this.delimiter = delimiter;
        }

        public SQLExecutor(String type, String url, String userName, String password) {
            this(type, url, userName, password, ";");
        }

        public void importFile(String sqlContent) throws Exception {
            SQLExec sqlExec = new SQLExec();
            sqlExec.setDriver(type);
            sqlExec.setDelimiter(delimiter);
            sqlExec.setUrl(this.url);
            sqlExec.setUserid(this.userName);
            sqlExec.setPassword(this.password);
            sqlExec.addText(sqlContent);
            sqlExec.setOnerror((SQLExec.OnError) ((SQLExec.OnError) EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));
            sqlExec.setPrint(true);
            sqlExec.setProject(new Project());
            sqlExec.execute();
        }
    }
}
