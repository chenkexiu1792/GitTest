package com.honghe.common.common.enums;

public enum MtDeviceEnum {
    /**
     * 中控
     *
     */
    CENTRAL("CentralControl", "Central"),
    /**
     * AV设备
     *
     */
    AV_DDEVICE("AvDevice", "AvDevice");

    String deviceClass;
    String type;

    MtDeviceEnum(String deviceClass, String type) {
        this.deviceClass = deviceClass;
        this.type = type;
    }

    public String deviceClass() {
        return deviceClass;
    }

    public String type() {
        return type;
    }
}
