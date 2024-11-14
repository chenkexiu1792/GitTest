package com.honghe.common.common.enums;

/**
 * @description: 前端定义的文件类型
 * @author: wuxiao
 * @create: 2019-04-22 13:55
 **/
public enum FrontResType {
    //文本
    TEXT(1),
    //图片
    IMG(2),
    //视频
    VIDEO(8),
    //背景音乐
    BGMUSIC(10)
    ;

    int type;

    FrontResType(int type) {
        this.type = type;
    }

    public int value(){
        return type;
    }
}
