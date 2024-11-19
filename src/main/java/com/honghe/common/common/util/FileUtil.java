package com.honghe.common.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

public class FileUtil {
    private static Logger logger= LoggerFactory.getLogger(FileUtil.class);

    private static String userDirFileName = "user.dir";
    private static String tempDirFileName = "temp";
    /**
     * 获取项目临时目录
     * @param path 相对路径
     * @return
     */
    public static String getTempDirection(String path){
        String resultPath = getUserDirection() + tempDirFileName + File.separator;

        File file = new File(resultPath);
        if (!file.exists()) {
            if (!file.mkdir()){
                logger.error("【FileController】fileUpload：make file director error");
            }
        }

        // 传递的路径
        if (null != path && path.length() > 0){
            resultPath += path + File.separator;

            file = new File(resultPath);
            if (!file.exists()) {
                if (!file.mkdir()){
                    logger.error("【FileController】fileUpload：make file director error");
                }
            }
        }
        return  resultPath;
    }
    /**
     * 获取用户当前目录
     * @return
     */
    public static String getUserDirection(){
        String userCurrentDir = System.getProperty(userDirFileName);
        String resultPath = "";
        if (null != userCurrentDir){ // 用户目录
            resultPath += userCurrentDir + File.separator;
        }

        return  resultPath;
    }


    /**
     * 获取项目临时目录
     * @return
     */
    public static String getTempDirection(){
        String resultPath = getUserDirection() + tempDirFileName + File.separator;

        File file = new File(resultPath);
        if (!file.exists()) {
            if (!file.mkdir()){
                logger.error("【FileController】fileUpload：make file director error");
            }
        }

        return  resultPath;
    }


    /**
     * 获取文件的扩展名
     * @param filename 文件名
     * @return
     */
    public static String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(46);
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1).toLowerCase();
            }
        }

        return filename;
    }

    /**
     * 下载文件的写入函数
     * @param req 请求对象
     * @param resp 相应对象
     * @param fileName 下载的文件名称
     */
    public static void downloadTempPathRequest(HttpServletRequest req, HttpServletResponse resp, String fileName){
        String realPath = FileUtil.getTempDirection() + fileName;
        File resultFile = new File(realPath);
        if (!resultFile.exists()) {
            return;
        }

        downloadFileRequest(req,resp,realPath,fileName);
    }

    /**
     * 下载文件的写入函数
     * @param req 请求对象
     * @param resp 相应对象
     * @param fileName 下载的文件名称
     */
    public static void downloadFileRequest(HttpServletRequest req, HttpServletResponse resp, String filePath,String fileName){
        try {
            File resultFile = new File(filePath);
            if (!resultFile.exists()) {
                return;
            }

            // 处理中文文件名
            String resultFileName = fileName;
            if (fileName.contains(".")){
                int index = fileName.lastIndexOf(".");
                String name = fileName.substring(0,index);    // 文件名
                String extension = fileName.substring(index); // 扩展名
                resultFileName = new String(name.getBytes(),"ISO-8859-1") + extension;  // 编码后的文件名
            }

            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=" + resultFileName);
            req.setCharacterEncoding("UTF-8");

            InputStream is = null;
            FileInputStream fileInputStream = null;
            ServletOutputStream os = resp.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            try {
                fileInputStream = new FileInputStream(resultFile);
                is = new BufferedInputStream(fileInputStream);
                byte[] buffer = new byte[4 * 1024]; //4k Buffer
                int read = 0;
                while ((read = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭输出字节流和response输出流
                try {
                    if (null != fileInputStream) {
                        fileInputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != bos) {
                        bos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != os) {
                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 写文件
     *
     * @param data
     * @param file
     */
    public static void writerJsonFile(Map data, File file) {
        PrintWriter pw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String str = JSON.toJSONString(data);
            //设置文件格式  utf-8
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            pw = new PrintWriter(out);
            pw.print(str);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件夹下文件删除
     *
     * @param f
     */
    public static void recurDelete(File f) {
        if (f.listFiles() != null) {
            for (File fi : f.listFiles()) {
                if (fi.isDirectory()) {
                    recurDelete(fi);
                } else {
                    fi.delete();
                }
            }
        }
    }

    /**
     * 下载文件
     */
    public static void downFile(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        String downFilename = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        response.setContentType("application/x-msdownload");
        response.setContentLength((int) file.length());
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(downFilename.getBytes("GBK"), "ISO8859_1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = response.getOutputStream();
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
