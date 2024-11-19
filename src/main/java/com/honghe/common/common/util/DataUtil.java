package com.honghe.common.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据工具类
 *
 * @auther youye
 * @Time 2019/02/27 9:43
 */
public class DataUtil {
    /**
     * 生成token
     * 生成规则：前缀_uuid_MD532(userName)
     * @param prefix 前缀
     * @param userName 用户名
     * @return
     */
    public static String getToken(String prefix,String userName){
        if(MD5(userName)==null) return null;
        return prefix+"_"+ UUID.randomUUID().toString().replaceAll("-","")+"_"+MD5(userName);
    }

    /**
     * 进行MD5加密，
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        if(s==null) return null;
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取UUID
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }

    public static boolean isSuperAdmin(String userId){
        return (null == userId ? false : "1".equals(userId));
    }

    /**
     * 对字符串进行MD5加密
     * @param source 字符集
     */
    public static String MD5(byte[] source) {
        // byte[] source = string.getBytes();
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            char[] str = new char[32];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * MD5加码。32位
     * @param inStr 字符串
     */
    public static String MD5License(String inStr) {
        MessageDigest md5 = null;
        inStr = inStr + "hitevision";

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }



    /**
     * 正则去掉所有字符操作
     * @param chines
     * @return
     */
    public static String cleanChar(String chines) {
        chines = chines.replaceAll("[\\p{Punct}\\p{Space}]+", "");
        // 正则表达式去掉所有中文的特殊符号
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}<>《》【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(chines);
        chines = matcher.replaceAll("").trim();
        return chines;
    }


    /****************************************************************************************************/
    /******************************************** 随机字符串 ********************************************/
    /****************************************************************************************************/

    /**
     * 随机生成N为随机数
     * @param count 生成几位
     * @return  string
     */
    public static String randomNumber(int count){
        StringBuilder sb = new StringBuilder();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    /**
     * 随机生成N为随机数
     * @return  string
     */
    public static String randomMeetingCode(){

        // 根据广发规定：随机数生成是180~999
        int min = 180;
        int max = 999;
        Integer value  = new Random().nextInt(max);

        // 随机数大于Max，得到<Max值
        if (value < min){
            value += min;
        }

        if (value > max){
            value = value % max + 1;
        }

        if (value < min){
            // 随机数小于Min
            int newValue = min + value;
            if (newValue > max){
                value = min + (value - min);
            }
        }

        return String.valueOf(value);
    }


    public static int getHourByTime(String time,int gap){
        int hour = -1;

        // 截取时间形成字符串数组：["08","30","00"]
        String[] timeList = time.split(":");

        // 解析小时：08
        if (timeList.length >= 1){
            String hourStr  = timeList[0];
            hour = intValue(hourStr);
        }

        // 解析分钟：30
        if (timeList.length >= 2){
            String minute = timeList[1];
            if (DataUtil.intValue(minute) > 0 && gap != 0){
                if (hour + gap >= 0 && hour + gap < 24){
                    hour += gap;
                }
            }
        }

        return hour;
    }

    /****************************************************************************************************/
    /******************************************** 数据处理 **********************************************/
    /****************************************************************************************************/

    /**
     * 转化为字符串
     * @param value 数据
     */
    public static String strValue(Object value){
        if (value == null){
            return "";
        }
        return String.valueOf(value);
    }

    /**
     * 转化为字符串
     * @param value 数据
     */
    public static int intValue(Object value){
        try {
            if (null != value && !("".equals(value))){
                return Integer.valueOf(String.valueOf(value));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 转化为字符串
     * @param value 数据
     */
    public static long longValue(Object value){
        try {
            if (null != value && !("".equals(value))){
                return Long.valueOf(String.valueOf(value));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 转化为数组
     * @param list 数据集
     */
    public static List listValue(List list){
        try {
            if (null != list && list instanceof List){
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList();
    }

    /**
     * 字符串转Long型
     * @param value
     * @param defaultVaule 默认值
     * @return
     */
    public static long toLong(String value, long defaultVaule) {
        if (null == value || "".equals(value)) {
            return defaultVaule;
        }

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultVaule;
        }
    }

    /**
     * 字符串转int 型
     * @param value
     * @param defaultVaule 默认值
     * @return
     */
    public static int toInt(String value, int defaultVaule) {
        if (null == value || "".equals(value)) {
            return defaultVaule;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultVaule;
        }
    }

    public static Integer base64ImageSize(String image){
        if (null == image || image.length() < 22){
            return -1;
        }

        String str=image.substring(22); // 1.需要计算文件流大小，首先把头部的data:image/png;base64,（注意有逗号）去掉。
        Integer equalIndex= str.indexOf("=");//2.找到等号，把等号也去掉
        if(str.indexOf("=")>0) {
            str=str.substring(0, equalIndex);
        }
        Integer strLength=str.length();//3.原来的字符流大小，单位为字节
        Integer size=strLength-(strLength/8)*2;//4.计算后得到的文件流大小，单位为字节
        return size;
    }


    public static Map convertBean(Object bean) {
        Map returnMap = new HashMap();
        try {
            Class type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result);
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return returnMap;
    }

    /****************************************************************************************************/
    /**************************************** 模拟数据使用函数 ******************************************/
    /****************************************************************************************************/
    //数字位
    public static String[] chnNumChar = {"零","一","二","三","四","五","六","七","八","九"};
    public static char[] chnNumChinese = {'零','一','二','三','四','五','六','七','八','九'};
    //节权位
    public static String[] chnUnitSection = {"","万","亿","万亿"};
    //权位
    public static String[] chnUnitChar = {"","十","百","千"};
    public static HashMap intList = new HashMap();
    static{
        for(int i=0;i<chnNumChar.length;i++){
            intList.put(chnNumChinese[i], i);
        }

        intList.put('十',10);
        intList.put('百',100);
        intList.put('千', 1000);
    }

    public static String numberToChinese(int num){//转化一个阿拉伯数字为中文字符串
        if(num == 0){
            return "零";
        }
        int unitPos = 0;//节权位标识
        String All = new String();
        String chineseNum = new String();//中文数字字符串
        boolean needZero = false;//下一小结是否须要补零
        String strIns = new String();
        while(num>0){
            int section = num%10000;//取最后面的那一个小节
            if(needZero){//推断上一小节千位是否为零。为零就要加上零
                All = chnNumChar[0] + All;
            }
            chineseNum = sectionTOChinese(section,chineseNum);//处理当前小节的数字,然后用chineseNum记录当前小节数字
            if( section!=0 ){//此处用if else 选择语句来运行加节权位
                strIns = chnUnitSection[unitPos];//当小节不为0。就加上节权位
                chineseNum = chineseNum + strIns;
            }else{
                strIns = chnUnitSection[0];//否则不用加
                chineseNum = strIns + chineseNum;
            }
            All = chineseNum+All;
            chineseNum = "";
            needZero = (section<1000) && (section>0);
            num = num/10000;
            unitPos++;
        }
        return All;
    }


    public static String sectionTOChinese(int section,String chineseNum){
        String setionChinese = new String();//小节部分用独立函数操作
        int unitPos = 0;//小节内部的权值计数器
        boolean zero = true;//小节内部的制零推断。每一个小节内仅仅能出现一个零
        while(section>0){
            int v = section%10;//取当前最末位的值
            if(v == 0){
                if( !zero ){
                    zero = true;//须要补零的操作。确保对连续多个零仅仅是输出一个
                    chineseNum = chnNumChar[0] + chineseNum;
                }
            }else{
                zero = false;//有非零的数字。就把制零开关打开
                setionChinese = chnNumChar[v];//相应中文数字位
                setionChinese = setionChinese + chnUnitChar[unitPos];//相应中文权位
                chineseNum = setionChinese + chineseNum;
            }
            unitPos++;
            section = section/10;
        }

        return chineseNum;
    }

    /**
     * 是否含有sql注入，返回true表示含有
     * @param obj
     * @return
     */
    public static boolean containsSqlInjection(Object obj){
        Pattern pattern= Pattern.compile("\\b(and|exec|insert|select|drop|grant|alter|delete|update|count|chr|mid|master|truncate|char|declare|or)\\b|(\\*|;|\\+|'|%)");
        Matcher matcher=pattern.matcher(obj.toString());
        return matcher.find();
    }
}