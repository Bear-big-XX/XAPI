package com.xx.xapicommon.model.enums;

/**
 * 接口状态信息枚举
 */
public enum InterInfoStatusEnum {
    OFFLINE("关闭",0),
    ONLINE("上线",1);

    private final String text;
    private final int value;

    InterInfoStatusEnum(String text, int value){
        this.text = text;
        this.value = value;
    }

    public  Integer getValue(){
        return this.value;
    }
}
