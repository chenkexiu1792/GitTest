package com.honghe.common.common.enums;

/**
 * 资源类型
 */
public enum ResourceType {
    Undefine(0), // 未定义
    Video(1),    // 视频
    File(2),     // 文档
    Image(3),    // 图片
    Program(4),  // 节目
    Template(5), // 模板
    Music(6),    // 音乐
    Composite(7),// 组件
    Folder(8),   // 文件夹
    Other(100);  // 其他

    int type;

    ResourceType(int type) {
        this.type = type;
    }

    public int value(){
        return type;
    }

    public String toString()
    {
        return String.valueOf(type);
    }


    public static final String VIDEO_TYPE = "avi,wmv,mov,rm,rmvb,mpg,mpeg,mp4,vob,flv,f4v,mkv,3gp,m4v,ts,asf";
    public static final String AUDIO_TYPE = "mp3,wma,mpeg-4,midi,realaudio";
    public static final String IMAGE_TYPE = "jpg,jpeg,png,bmp,gif";
    public static final String DOC_TYPE = "doc,docx,ppt,pptx,xlsx,xls,pdf";
    public static final String DSS_TYPE = "dss";
    public static final String VIDEO_TYPE_NOTRANS = "swf";

    public static int getFileType(String format){
        int fileType = Other.value();
        if (null != format && !format.equals("")) {
            if (VIDEO_TYPE.indexOf(format) != -1) {
                fileType = Video.value();
            } else if (DOC_TYPE.indexOf(format) != -1) {
                fileType = File.value();
            } else if (IMAGE_TYPE.indexOf(format) != -1) {
                fileType = Image.value();
            } else if (DSS_TYPE.indexOf(format) != -1) {
                fileType = Program.value();
            } else if (AUDIO_TYPE.indexOf(format) != -1) {
                fileType = Music.value();
            }
        }
        return fileType;
    }
}
