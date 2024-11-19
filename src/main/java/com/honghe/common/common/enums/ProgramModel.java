package com.honghe.common.common.enums;

/**
 * 节目类型(节目/模板)
 */
public enum ProgramModel {
    Program(0),    // 自定义节目
    UserModel(1),  // 用户模板
    SysModel(2);   // 系统模板

    int model;

    ProgramModel(int model) {
        this.model = model;
    }

    public int value(){
        return model;
    }

    public String toString()
    {
        return String.valueOf(model);
    }
}
