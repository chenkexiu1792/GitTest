package com.honghe.common.common.util;

import java.io.File;

/**
 * 项目路径工具类
 *
 * @auther yuk
 * @Time 2018/3/14 10:31
 */
public class PathUtil {

    public static String getPath(PathType pathType) {

        String path = System.getProperty("user.dir") + File.separator + pathType.value() + File.separator;
        if (pathType == PathType.UPLOAD || pathType == PathType.SOURCE || pathType == PathType.TEMP) {
            File file = new File(path);
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        }
        return path;
    }

    public enum PathType {
        CONFIG("config"),
        UPLOAD("upload_dir"),
        SOURCE("source"),
        TEMP("temp");
        private String type = "upload_dir";

        PathType(String type) {
            this.type = type;
        }


        public String value() {
            return this.type;
        }
    }

}
