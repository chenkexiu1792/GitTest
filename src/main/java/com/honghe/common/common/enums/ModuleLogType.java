package com.honghe.common.common.enums;

public enum ModuleLogType {
    Login(1),             // 登录
    PersonalCenter(2),    // 个人中心
    CompanySetting(3),    // 企业设置
    ProjectSetting(4),    // 项目设置
    RoomTypeSetting(5),   // 房间类型设置
    DeviceTypeSetting(6), // 设备类型设置
    NoticeSetting(7),     // 通知设置
    CompanyUserSetting(8),// 企业用户设置

    SupplierManager(31),  // 厂商管理
    FileManager(32),      // 文件管理
    DeviceManager(33),    // 设备管理
    DeviceMonitor(34);    // 设备监控


    int type;

    ModuleLogType(int type) {
        this.type = type;
    }

    public int value() {
        return type;
    }

    public String toString() {
        return String.valueOf(type);
    }

    public static String toModuleString(int type) {
        switch (type){
            case 1: return "登录";
            case 2: return "个人中心";
            case 3: return "企业设置";
            case 4: return "项目设置";
            case 5: return "房间类型设置";
            case 6: return "设备类型设置";
            case 7: return "通知设置";
            case 8: return "企业用户设置";

            case 31: return "厂商管理";
            case 32: return "文件管理";
            case 33: return "设备管理";
            case 34: return "设备监控";
        }

        return "";
    }
}
