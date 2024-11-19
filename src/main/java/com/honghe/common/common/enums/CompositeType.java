package com.honghe.common.common.enums;

/**
 * @Author youye
 * @Date 2019/7/25 15:48
 * @Description 组件类型
 **/
public enum  CompositeType {
    Ranking(1),    // 排行榜组件
    Polling(2);    // 轮播图组件

    int type;

    CompositeType(int type) {
        this.type = type;
    }

    public int value(){
        return type;
    }

    public String toString()
    {
        return String.valueOf(type);
    }
}
