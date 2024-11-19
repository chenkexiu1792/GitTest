package com.honghe.common.common.enums;

public enum MtCompanyAreaType {
    // 未知
    Unknown(0),
    // 默认
    Default(35),
    // 楼层
    Floor(5),
    // 会议室
    Meeting(6),
    // 普通会议室
    CommonMeeting(17),
    // 会议室
    SeniorMeeting(43);

    int type;

    MtCompanyAreaType(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }

    public String value()
    {
        return String.valueOf(type);
    }
}
