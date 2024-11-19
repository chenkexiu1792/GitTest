package com.honghe.common.common.util;

import java.util.regex.Pattern;

public class StringUtil {


    /**
     * 推荐，速度最快
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 字符串是否为空串或null
     *
     * @param checkStr
     * @return
     */
    public static boolean isEmpty(String checkStr) {
        return checkStr == null || "".equals(checkStr);
    }

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String[] encode = new String[]{
                "UTF-8",
                "ISO-8859-1",
                "GB2312",
                "GBK",
                "GB18030",
                "Big5",
                "Unicode",
                "ASCII"
        };
        for (int i = 0; i < encode.length; i++) {
            try {
                String s = new String(str.getBytes(encode[i]), encode[i]);
                if (str.equals(s)) {
                    return encode[i];
                }
            } catch (Exception ex) {
            }
        }

        return "";
    }
}
