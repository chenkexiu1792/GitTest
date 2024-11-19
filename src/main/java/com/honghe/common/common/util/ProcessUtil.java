package com.honghe.common.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author caoqian
 * @date 2019/7/24 15:54
 * @description 进程工具类
 */
public class ProcessUtil {
    private ProcessUtil() {
    }

    public static final boolean execute(String command) {
        BufferedReader bufferedReader = null;

        boolean var3;
        try {
            Process pro = Runtime.getRuntime().exec(command);
            bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            while(bufferedReader.readLine() != null) {
                ;
            }

            try {
                pro.waitFor();
            } catch (Exception var16) {
                var16.printStackTrace();
                boolean var4 = false;
                return var4;
            }

            pro.exitValue();
            return true;
        } catch (Exception var17) {
            var3 = false;
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception var15) {
                ;
            }

        }

        return var3;
    }

    public static final boolean kill(String name) {
        return execute("wmic process where name=\"" + name + "\" call terminate ");
    }

    public static final boolean kill(int port) {
        Runtime runtime = Runtime.getRuntime();

        try {
            Process p = runtime.exec("cmd /c netstat -ano | findstr \"" + port + "\"");
            InputStream inputStream = p.getInputStream();
            List<String> read = read(port, inputStream, "UTF-8");
            if (read.size() != 0) {
                kill(read);
                return true;
            } else {
                return false;
            }
        } catch (Exception var5) {
            return false;
        }
    }

    private static void kill(List<String> data) throws Exception {
        Set<Integer> pids = new HashSet();
        Iterator var2 = data.iterator();

        while(var2.hasNext()) {
            String line = (String)var2.next();
            int offset = line.lastIndexOf(" ");
            String spid = line.substring(offset);
            spid = spid.replaceAll(" ", "");
            int pid = Integer.parseInt(spid);
            pids.add(pid);
        }

        killWithPid(pids);
    }

    private static void killWithPid(Set<Integer> pids) throws Exception {
        Iterator var1 = pids.iterator();

        while(var1.hasNext()) {
            Integer pid = (Integer)var1.next();
            execute("taskkill /F /pid " + pid + "");
        }

    }

    private static List<String> read(int port, InputStream in, String charset) throws Exception {
        List<String> data = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));

        String line;
        while((line = reader.readLine()) != null) {
            if (getPort(line) == port) {
                data.add(line);
                break;
            }
        }

        reader.close();
        return data;
    }

    private static int getPort(String str) {
        Pattern pattern = Pattern.compile("^ *[a-zA-Z]+ +\\S+");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        String find = matcher.group();
        int spstart = find.lastIndexOf(":");
        find = find.substring(spstart + 1);
        return Integer.parseInt(find);
    }
}
