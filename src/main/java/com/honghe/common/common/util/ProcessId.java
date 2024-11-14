package com.honghe.common.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * 读取当前程序pid，用于关闭
 *
 * @auther Libing
 * @Time 2017/11/20 16:36
 */
public class ProcessId {

    public static void setProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        Integer pid = Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
        System.out.println("PID:" + pid);
        try {
            File file = new File("pid.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.println(pid);
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
